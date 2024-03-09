package com.primogemstudio.advancedfmk.render.uiframework.ui

import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.render.Shaders
import com.primogemstudio.advancedfmk.render.uiframework.BaseTexture
import com.primogemstudio.advancedfmk.render.uiframework.ValueFetcher
import com.primogemstudio.advancedfmk.render.uiframework.invoke
import com.primogemstudio.advancedfmk.render.uiframework.ui.RendererConstraints.internalTarget
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import org.joml.Matrix4f
import org.joml.Vector4f

object RendererConstraints {
    val internalTarget = TextureTarget(1, 1, true, Minecraft.ON_OSX)
}

abstract class UIObject(
    var location: MutableMap<String, ValueFetcher> = mutableMapOf(),
    var filter: CompositeFilter? = null
) {
    data class CompositeFilter(
        var type: String,
        var args: Map<String, Any> = mutableMapOf()
    ) {
        fun init(vars: GlobalVars) {
            internalTarget.resize(vars.screen_size.x.toInt(), vars.screen_size.y.toInt(), Minecraft.ON_OSX)
            internalTarget.setClearColor(0f, 0f, 0f, 0f)
            internalTarget.clear(Minecraft.ON_OSX)
            internalTarget.bindWrite(true)
        }
        fun render(vars: GlobalVars) {
            val shader = when (type) {
                "advancedfmk:gaussian_blur" -> Shaders.GAUSSIAN_BLUR
                else -> null
            }

            shader?.setSamplerUniform("InputSampler", internalTarget)
            args.forEach { (t, u) ->
                if (u is Int) shader?.setUniformValue(t, u)
                if (u is Float) shader?.setUniformValue(t, u)
                if (u is Boolean) shader?.setUniformValue(t, if (u) 1 else 0)
            }
            shader?.render(vars.tick)
        }
    }

    abstract fun render(vars: GlobalVars, matrix: Matrix4f)
    abstract fun registerTex()
    abstract var disableAlpha: Boolean
    abstract var clip: RenderTarget?
}

data class UIRect(
    var thickness: Float = 0f,
    var smoothedge: Float = 0.0005f,
    var radius: Float = 25f,
    var color: Vector4f = Vector4f(0f),
    var texture: ResourceLocation? = null
): UIObject() {
    override fun render(vars: GlobalVars, matrix: Matrix4f) {
        val alp = if (disableAlpha) 1f else color.w
        val s = Vector4f(
            location["x"]!!(vars.toMap()),
            location["y"]!!(vars.toMap()),
            location["w"]!!(vars.toMap()),
            location["h"]!!(vars.toMap())
        )

        texture?.also { RenderSystem.setShaderTexture(0, it) }
        (if (clip == null) (if (texture == null) Shaders.ROUNDED_RECT else Shaders.ROUNDED_RECT_TEX) else (if (texture == null) Shaders.ROUNDED_RECT_CLIP else Shaders.ROUNDED_RECT_TEX_CLIP)).apply {
            RenderSystem.setShader { this }
            getUniform("Resolution")!!.set(floatArrayOf(vars.screen_size.x, vars.screen_size.y))
            getUniform("Center")!!.set(floatArrayOf(s[0] + s[2] / 2f, s[1] + s[3] / 2f))
            getUniform("Radius")!!.set(radius)
            getUniform("Thickness")!!.set(thickness)
            getUniform("SmoothEdge")!!.set(smoothedge)
            getUniform("Size")!!.set(floatArrayOf(s[2], s[3]))
            if (clip != null) setSampler("ClipSampler", clip!!)
        }

        val buff = Tesselator.getInstance().builder
        buff.begin(VertexFormat.Mode.QUADS, if (texture == null) DefaultVertexFormat.POSITION_COLOR else DefaultVertexFormat.POSITION_COLOR_TEX)
        buff.vertex(matrix, s[0], s[1], 0f).color(color.x, color.y, color.z, alp).apply { if (texture != null) uv(0f, 0f) }.endVertex()
        buff.vertex(matrix, s[0], s[1] + s[3], 0f).color(color.x, color.y, color.z, alp).apply { if (texture != null) uv(0f, 1f) }.endVertex()
        buff.vertex(matrix, s[0] + s[2], s[1] + s[3], 0f).color(color.x, color.y, color.z, alp).apply { if (texture != null) uv(1f, 1f) }.endVertex()
        buff.vertex(matrix, s[0] + s[2], s[1], 0f).color(color.x, color.y, color.z, alp).apply { if (texture != null) uv(1f, 0f) }.endVertex()
        RenderSystem.enableBlend()
        BufferUploader.drawWithShader(buff.end())
        RenderSystem.disableBlend()
    }

    override fun registerTex() {
        Minecraft.getInstance().textureManager.register(texture!!, BaseTexture(NativeImage.read(UIRect::class.java.classLoader.getResourceAsStream("assets/${texture!!.namespace}/${texture!!.path}"))))
    }

    override var disableAlpha: Boolean = false
    override var clip: RenderTarget? = null
}



data class UICompound(
    var components: Map<ResourceLocation, UIObject> = mutableMapOf(),
    var topComponent: ResourceLocation = ResourceLocation("minecraft:null")
): UIObject() {
    override fun render(vars: GlobalVars, matrix: Matrix4f) {
        val top = findTop()
        assert(top != null) { "Top component not found or invalid. " }
        val subc = components.values.toMutableList().apply {
            if (indexOf(top) == 0) return@apply
            remove(top)
            add(0, top!!)
        }

        clip?.setClearColor(0f, 0f, 0f, 0f)
        clip?.resize(vars.screen_size.x.toInt(), vars.screen_size.y.toInt(), Minecraft.ON_OSX)
        clip?.clear(Minecraft.ON_OSX)
        clip?.bindWrite(true)

        top!!.apply {
            disableAlpha = true
            render(vars, matrix)
            disableAlpha = false
        }

        subc.forEach { u -> if (u != top) u.clip = this.clip }
        subc.forEach { u ->
            Minecraft.getInstance().mainRenderTarget.bindWrite(true)
            u.filter?.init(vars)
            u.render(vars, matrix)
            u.filter?.render(vars)
        }
    }

    private fun findTop(): UIObject? = components.filter { it.key == topComponent }.values.toList().let { if (it.isNotEmpty()) it[0] else null }
    override fun registerTex() = components.values.forEach { it.registerTex() }

    override var disableAlpha: Boolean = false
    override var clip: RenderTarget? = TextureTarget(1, 1, true, Minecraft.ON_OSX)
}

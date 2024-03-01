package com.primogemstudio.advancedfmk.render.uiframework.ui

import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.render.Shaders
import com.primogemstudio.advancedfmk.render.uiframework.ValueFetcher
import com.primogemstudio.advancedfmk.render.uiframework.invoke
import glm_.vec4.Vec4
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import org.joml.Matrix4f

abstract class UIObject(
    var location: MutableMap<String, ValueFetcher> = mutableMapOf(),
    var filter: CompositeFilter? = null
) {
    data class CompositeFilter(
        var type: ResourceLocation,
        var args: Map<String, Any> = mutableMapOf()
    )

    abstract fun render(vars: GlobalVars, matrix: Matrix4f)
    abstract var disableAlpha: Boolean
    abstract var clip: RenderTarget?
}

data class UIRect(
    var thickness: Float = 0f,
    var smoothedge: Float = 0.0005f,
    var radius: Float = 25f,
    var color: Vec4 = Vec4(0f)
): UIObject() {
    override fun render(vars: GlobalVars, matrix: Matrix4f) {
        val alp = if (disableAlpha) 1f else color.w
        val s = Vec4(
            location["x"]!!(vars.toMap()),
            location["y"]!!(vars.toMap()),
            location["w"]!!(vars.toMap()),
            location["h"]!!(vars.toMap())
        )
        (if (clip == null) Shaders.ROUNDED_RECT else Shaders.ROUNDED_RECT_CLIP).apply {
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
        buff.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)
        buff.vertex(matrix, s[0], s[1], 0f).color(color.x, color.y, color.z, alp).endVertex()
        buff.vertex(matrix, s[0], s[1] + s[3], 0f).color(color.x, color.y, color.z, alp).endVertex()
        buff.vertex(matrix, s[0] + s[2], s[1] + s[3], 0f).color(color.x, color.y, color.z, alp).endVertex()
        buff.vertex(matrix, s[0] + s[2], s[1], 0f).color(color.x, color.y, color.z, alp).endVertex()
        RenderSystem.enableBlend()
        BufferUploader.drawWithShader(buff.end())
        RenderSystem.disableBlend()
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

        Minecraft.getInstance().mainRenderTarget.bindWrite(true)
        subc.forEach { u ->
            val a = TextureTarget(vars.screen_size.x.toInt(), vars.screen_size.y.toInt(), true, Minecraft.ON_OSX)
            a.setClearColor(0f, 0f, 0f, 0f)
            a.clear(Minecraft.ON_OSX)
            a.bindWrite(true)
            u.render(vars, matrix)
            Shaders.GAUSSIAN_BLUR.setSamplerUniform("InputSampler", a)
            Shaders.GAUSSIAN_BLUR.setUniformValue("DigType", 0)
            Shaders.GAUSSIAN_BLUR.setUniformValue("Radius", 16)
            Shaders.GAUSSIAN_BLUR.render(vars.tick)
        }
    }

    fun findTop(): UIObject? = components.filter { it.key == topComponent }.values.toList().let { if (it.isNotEmpty()) it[0] else null }

    override var disableAlpha: Boolean = false
    override var clip: RenderTarget? = TextureTarget(1, 1, true, Minecraft.ON_OSX)
}

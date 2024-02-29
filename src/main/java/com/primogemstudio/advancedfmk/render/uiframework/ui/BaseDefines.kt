package com.primogemstudio.advancedfmk.render.uiframework.ui

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.render.Shaders
import com.primogemstudio.advancedfmk.render.uiframework.ValueFetcher
import com.primogemstudio.advancedfmk.render.uiframework.invoke
import glm_.vec4.Vec4
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
        RenderSystem.setShader { Shaders.ROUNDED_RECT }
        Shaders.ROUNDED_RECT.getUniform("Resolution")!!.set(floatArrayOf(vars.screen_size.x, vars.screen_size.y))
        Shaders.ROUNDED_RECT.getUniform("Center")!!.set(floatArrayOf(s[0] + s[2] / 2f, s[1] + s[3] / 2f))
        Shaders.ROUNDED_RECT.getUniform("Radius")!!.set(radius)
        Shaders.ROUNDED_RECT.getUniform("Thickness")!!.set(thickness)
        Shaders.ROUNDED_RECT.getUniform("SmoothEdge")!!.set(smoothedge)
        Shaders.ROUNDED_RECT.getUniform("Size")!!.set(floatArrayOf(s[2], s[3]))
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
}

data class UICompound(
    var components: Map<ResourceLocation, UIObject> = mutableMapOf(),
    var topComponent: ResourceLocation = ResourceLocation("minecraft:null")
): UIObject() {
    override fun render(vars: GlobalVars, matrix: Matrix4f) {

    }

    fun findTop(): UIObject? = components.filter { it.key == topComponent }.values.toList().let { if (it.isNotEmpty()) it[0] else null }

    override var disableAlpha: Boolean = false
}

package com.primogemstudio.advancedfmk.kui.elements

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.kui.GlobalData
import com.primogemstudio.advancedfmk.kui.Shaders
import com.primogemstudio.advancedfmk.kui.pipe.FilterBase
import net.minecraft.resources.ResourceLocation
import org.joml.Vector2f
import org.joml.Vector4f

class RectangleElement(
    override var id: String,
    override var pos: Vector2f,
    var size: Vector2f,
    var color: Vector4f,
    var radius: Float,
    var thickness: Float,
    var smoothedge: Float,
    var textureUV: Vector4f,
    var texturePath: ResourceLocation? = null,
    var filter: FilterBase? = null
) : RealElement(id, pos), FilteredElement {
    override fun filter(): FilterBase? = filter
    override fun subElement(id: String): UIElement? = null
    override fun renderActual(data: GlobalData) {
        filter?.init()

        val shader = if (texturePath != null) Shaders.ROUNDED_RECT_TEX else Shaders.ROUNDED_RECT
        texturePath?.also { RenderSystem.setShaderTexture(0, it) }
        RenderSystem.setShader { shader }
        shader.getUniform("Resolution")!!.set(data.screenWidth.toFloat(), data.screenHeight.toFloat())
        shader.getUniform("Center")!!.set(pos.x + size.x / 2, pos.y + size.y / 2)
        shader.getUniform("Radius")!!.set(radius)
        shader.getUniform("Thickness")!!.set(thickness)
        shader.getUniform("SmoothEdge")!!.set(smoothedge)
        shader.getUniform("Size")!!.set(size.x, size.y)

        val buff = Tesselator.getInstance().begin(
            VertexFormat.Mode.QUADS,
            if (texturePath != null) Shaders.POSITION_COLOR_TEX else DefaultVertexFormat.POSITION_COLOR
        )

        val matrix = data.graphics.pose().last().pose()
        val bdsize = if (texturePath != null) 0 else 8
        if (texturePath == null) {
            buff.addVertex(matrix, pos.x - bdsize, pos.y - bdsize, 0f).setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x - bdsize, pos.y + size.y + bdsize, 0f)
                .setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x + size.x + bdsize, pos.y + size.y + bdsize, 0f)
                .setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x + size.x + bdsize, pos.y - bdsize, 0f)
                .setColor(color.x, color.y, color.z, color.w)
        }
        else {
            buff.addVertex(matrix, pos.x - bdsize, pos.y - bdsize, 0f)
                .setUv(textureUV[0], textureUV[2])
                .setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x - bdsize, pos.y + size.y + bdsize, 0f)
                .setUv(textureUV[0], textureUV[3])
                .setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x + size.x + bdsize, pos.y + size.y + bdsize, 0f)
                .setUv(textureUV[1], textureUV[3])
                .setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x + size.x + bdsize, pos.y - bdsize, 0f)
                .setUv(textureUV[1], textureUV[2])
                .setColor(color.x, color.y, color.z, color.w)
        }
        if (filter != null) RenderSystem.disableBlend() else RenderSystem.enableBlend()
        BufferUploader.drawWithShader(buff.buildOrThrow())
        if (filter != null) RenderSystem.enableBlend() else RenderSystem.disableBlend()

        filter?.apply(data)
    }
    override fun renderWithoutFilter(data: GlobalData) {
        val f = filter
        filter = null
        render(data)
        filter = f
    }
}

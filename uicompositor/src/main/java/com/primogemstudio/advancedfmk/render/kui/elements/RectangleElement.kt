package com.primogemstudio.advancedfmk.render.kui.elements

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.render.Shaders
import com.primogemstudio.advancedfmk.render.kui.GlobalData
import net.minecraft.resources.ResourceLocation
import org.joml.Vector2f
import org.joml.Vector4f

class RectangleElement(
    override var pos: Vector2f,
    var size: Vector2f,
    var color: Vector4f,
    var radius: Float,
    var thickness: Float,
    var smoothedge: Float,
    var texturePath: ResourceLocation? = null
) : RealElement(pos) {
    private val avg = { a: Float, b: Float -> (a + b) / 2 }
    override fun render(data: GlobalData) {
        val shader = if (texturePath != null) Shaders.ROUNDED_RECT_TEX else Shaders.ROUNDED_RECT
        texturePath?.also { RenderSystem.setShaderTexture(0, it) }
        RenderSystem.setShader { shader }
        shader.getUniform("Resolution")!!.set(data.screenWidth.toFloat(), data.screenHeight.toFloat())
        shader.getUniform("Center")!!.set(avg(pos.x, size.x), avg(pos.y, size.y))
        shader.getUniform("Radius")!!.set(radius)
        shader.getUniform("Thickness")!!.set(thickness)
        shader.getUniform("SmoothEdge")!!.set(smoothedge)
        shader.getUniform("Size")!!.set(size.x, size.y)

        val buff = Tesselator.getInstance().begin(
            VertexFormat.Mode.QUADS,
            if (texturePath != null) Shaders.POSITION_COLOR_TEX else DefaultVertexFormat.POSITION_COLOR
        )

        val matrix = data.graphics.pose().last().pose()
        val bdsize = 8
        if (texturePath == null) {
            buff.addVertex(matrix, pos.x - bdsize, pos.y - bdsize, 0f).setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x - bdsize, pos.y + size.y + bdsize, 0f)
                .setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x + size.x + bdsize, pos.y + size.y + bdsize, 0f)
                .setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x + size.x + bdsize, pos.y - bdsize, 0f)
                .setColor(color.x, color.y, color.z, color.w)
        } else {
            buff.addVertex(matrix, pos.x - bdsize, pos.y - bdsize, 0f)
                .setUv(0f, 0f)
                .setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x - bdsize, pos.y + size.y + bdsize, 0f)
                .setUv(0f, 1f)
                .setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x + size.x + bdsize, pos.y + size.y + bdsize, 0f)
                .setUv(1f, 1f)
                .setColor(color.x, color.y, color.z, color.w)
            buff.addVertex(matrix, pos.x + size.x + bdsize, pos.y - bdsize, 0f)
                .setUv(1f, 0f)
                .setColor(color.x, color.y, color.z, color.w)
        }
        RenderSystem.enableBlend()
        BufferUploader.drawWithShader(buff.build()!!)
        RenderSystem.disableBlend()
    }
}
package com.primogemstudio.advancedfmk.kui.elements

import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.kui.GlobalData
import com.primogemstudio.advancedfmk.kui.Shaders
import com.primogemstudio.advancedfmk.kui.pipe.FilterBase
import com.primogemstudio.advancedfmk.live2d.Live2DModel
import com.primogemstudio.advancedfmk.live2d.target
import net.minecraft.client.Minecraft
import org.joml.Vector2f
import org.joml.Vector4f

class Live2DElement(
    override var id: String,
    override var pos: Vector2f,
    var size: Vector2f,
    var color: Vector4f,
    var radius: Float,
    var thickness: Float,
    var smoothedge: Float,
    var textureUV: Vector4f,
    modelPath: Pair<String, String>,
    var filter: FilterBase? = null
) : RealElement(id, pos), FilteredElement {
    private val model = Live2DModel(modelPath.first, modelPath.second)
    override fun filter(): FilterBase? = filter
    override fun subElement(id: String): UIElement? = null
    override fun renderActual(data: GlobalData) {
        target.clear(false)
        target.bindWrite(true)
        model.update(size.x.toInt(), size.y.toInt())
        Minecraft.getInstance().mainRenderTarget.bindWrite(true)

        filter?.init()

        val shader = Shaders.ROUNDED_RECT_TEX
        RenderSystem.setShaderTexture(0, target.colorTextureId)
        RenderSystem.setShader { shader }
        shader.getUniform("Resolution")!!.set(data.screenWidth.toFloat(), data.screenHeight.toFloat())
        shader.getUniform("Center")!!.set(pos.x + size.x / 2, pos.y + size.y / 2)
        shader.getUniform("Radius")!!.set(radius)
        shader.getUniform("Thickness")!!.set(thickness)
        shader.getUniform("SmoothEdge")!!.set(smoothedge)
        shader.getUniform("Size")!!.set(size.x, size.y)

        val buff = Tesselator.getInstance().begin(
            VertexFormat.Mode.QUADS,
            Shaders.POSITION_COLOR_TEX
        )

        val matrix = data.graphics.pose().last().pose()
        val bdsize = 8
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
        if (filter != null) RenderSystem.disableBlend() else RenderSystem.enableBlend()
        BufferUploader.drawWithShader(buff.buildOrThrow())
        if (filter != null) RenderSystem.enableBlend() else RenderSystem.disableBlend()

        filter?.apply(data)
    }

    override fun renderWithClip(data: GlobalData, texture: TextureTarget) {

    }

    override fun renderWithoutFilter(data: GlobalData) {
        val f = filter
        val a = color.w
        color.w = 1f
        filter = null
        render(data)
        filter = f
        color.w = a
    }
}

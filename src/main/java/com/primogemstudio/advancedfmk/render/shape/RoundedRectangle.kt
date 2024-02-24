package com.primogemstudio.advancedfmk.render.shape

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.render.RenderResource
import com.primogemstudio.advancedfmk.render.Shaders
import net.minecraft.network.chat.Component
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector4f

open class RoundedRectangle(x: Float, y: Float, w: Float, h: Float, message: Component?) :
    AbstractBackdropableShape(x.toInt(), y.toInt(), w.toInt(), h.toInt(), message) {
    protected var matrix = Matrix4f()
    protected val center = Vector2f()
    protected val size = Vector2f()
    protected val color = Vector4f()
    protected var radius = 10f
    protected var thickness = 0.00f
    protected var smoothedge = 0.001f

    init {
        resize(x, y, w, h)
    }

    fun resize(x: Float, y: Float, w: Float, h: Float): RoundedRectangle {
        size[w] = h
        center[x + w / 2] = y + h / 2
        return this
    }

    fun color(r: Float, g: Float, b: Float, a: Float): RoundedRectangle {
        color[r, g, b] = a
        return this
    }

    fun radius(rad: Float): RoundedRectangle {
        radius = rad
        return this
    }

    fun thickness(thi: Float): RoundedRectangle {
        thickness = thi
        return this
    }

    fun smoothedge(edg: Float): RoundedRectangle {
        smoothedge = edg
        return this
    }

    fun color(col: FloatArray?): RoundedRectangle {
        color.set(col)
        return this
    }

    override fun render(res: RenderResource?) {
        RenderSystem.setShader { Shaders.ROUNDED_RECT }
        Shaders.ROUNDED_RECT.getUniform("Resolution")!!.set(floatArrayOf(res!!.width.toFloat(), res.height.toFloat()))
        Shaders.ROUNDED_RECT.getUniform("Center")!!.set(floatArrayOf(center.x, center.y))
        Shaders.ROUNDED_RECT.getUniform("Radius")!!.set(radius)
        Shaders.ROUNDED_RECT.getUniform("Thickness")!!.set(thickness)
        Shaders.ROUNDED_RECT.getUniform("SmoothEdge")!!.set(smoothedge)
        Shaders.ROUNDED_RECT.getUniform("Size")!!.set(floatArrayOf(size.x, size.y))
        val x1 = center.x - size.x / 2
        val x2 = center.x + size.x / 2
        val y1 = center.y - size.y / 2
        val y2 = center.y + size.y / 2
        val buff = Tesselator.getInstance().builder
        buff.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)
        buff.vertex(matrix, x1, y1, 0f).color(color.x, color.y, color.z, color.w).endVertex()
        buff.vertex(matrix, x1, y2, 0f).color(color.x, color.y, color.z, color.w).endVertex()
        buff.vertex(matrix, x2, y2, 0f).color(color.x, color.y, color.z, color.w).endVertex()
        buff.vertex(matrix, x2, y1, 0f).color(color.x, color.y, color.z, color.w).endVertex()
        RenderSystem.enableBlend()
        BufferUploader.drawWithShader(buff.end())
        RenderSystem.disableBlend()
    }

    override fun updateStack(matrix: Matrix4f) {
        this.matrix = matrix
    }
}

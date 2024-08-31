package com.primogemstudio.advancedfmk.kui.elements

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.kui.GlobalData
import com.primogemstudio.advancedfmk.kui.pipe.FilterBase
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.RenderType
import org.joml.Vector2f
import org.joml.Vector4f

class GeometryLineElement(
    override var id: String,
    var width: Float,
    var color: Vector4f,
    var vertices: MutableList<Vector2f>,
    var filter: FilterBase? = null,
): RealElement(id, Vector2f(0f)), FilteredElement {
    override fun renderActual(data: GlobalData) {
        filter?.init()

        val m = data.graphics.pose().last().pose()
        RenderSystem.disableBlend()
        RenderSystem.disableCull()
        RenderSystem.depthMask(false)

        RenderType.lines()
        RenderSystem.setShader { GameRenderer.getRendertypeLinesShader() }
        RenderSystem.lineWidth(width)
        val buff = Tesselator.getInstance().begin(
            VertexFormat.Mode.LINES,
            DefaultVertexFormat.POSITION_COLOR_NORMAL
        )

        for (i in 0 ..< vertices.size - 1) {
            val v1 = vertices[i]
            val v2 = vertices[i + 1]
            buff.addVertex(m, v1.x, v1.y, 0f).setColor(color.x, color.y, color.z, color.w)
                .setNormal(v1.x - v2.x, v1.y - v2.y, 0f)
            buff.addVertex(m, v2.x, v2.y, 0f).setColor(color.x, color.y, color.z, color.w)
                .setNormal(v1.x - v2.x, v1.y - v2.y, 0f)
        }

        buff.build()?.apply { BufferUploader.drawWithShader(this) }
        RenderSystem.depthMask(true)
        RenderSystem.enableCull()
        RenderSystem.enableBlend()

        filter?.arg("Radius", 16)
        filter?.apply(data)
    }

    override fun subElement(id: String): UIElement? = null
    override fun filter(): FilterBase? = filter
    override fun renderWithoutFilter(data: GlobalData) {
        val f = filter
        filter = null
        render(data)
        filter = f
    }
}
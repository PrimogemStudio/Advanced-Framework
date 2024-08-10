package com.primogemstudio.advancedfmk.kui.elements

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.kui.GlobalData
import com.primogemstudio.advancedfmk.kui.pipe.FilterBase
import org.joml.Vector2f

class GeometryLineElement(
    override var id: String,
    var filter: FilterBase? = null
): RealElement(id, Vector2f(0f)), FilteredElement {
    override fun render(data: GlobalData) {
        filter?.init()

        RenderSystem.lineWidth(5f)
        val buff = Tesselator.getInstance().begin(
            VertexFormat.Mode.LINES,
            DefaultVertexFormat.POSITION_COLOR
        )
        val matrix = data.graphics.pose().last().pose()
        buff.addVertex(matrix, 0f, 0f, 0f).setColor(1f, 1f, 1f, 1f)
        buff.addVertex(matrix, 100f, 50f, 0f).setColor(1f, 1f, 1f, 1f)
        buff.addVertex(matrix, 100f, 50f, 0f).setColor(1f, 1f, 1f, 1f)
        buff.addVertex(matrix, 0f, 100f, 0f).setColor(1f, 1f, 1f, 1f)

        RenderSystem.disableBlend()
        BufferUploader.drawWithShader(buff.build()!!)
        RenderSystem.enableBlend()

        filter?.arg("Radius", 16)
        filter?.apply(data)
    }

    override fun subElement(id: String): UIElement? = null
    override fun filter(): FilterBase? = filter
}
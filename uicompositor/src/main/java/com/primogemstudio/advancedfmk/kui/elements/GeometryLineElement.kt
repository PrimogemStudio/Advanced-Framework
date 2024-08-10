package com.primogemstudio.advancedfmk.kui.elements

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.client.AdvancedFrameworkUICompositorClient
import com.primogemstudio.advancedfmk.kui.GlobalData
import com.primogemstudio.advancedfmk.kui.pipe.FilterBase
import net.minecraft.client.renderer.GameRenderer
import org.joml.Vector2f

class GeometryLineElement(
    override var id: String,
    var filter: FilterBase? = null
): RealElement(id, Vector2f(0f)), FilteredElement {
    override fun render(data: GlobalData) {
        filter?.init()

        // AdvancedFrameworkUICompositorClient.test()
        val m = data.graphics.pose().last().pose()
        RenderSystem.disableBlend()
        RenderSystem.disableCull()
        RenderSystem.depthMask(false)

        RenderSystem.setShader { GameRenderer.getRendertypeLinesShader() }
        RenderSystem.lineWidth(15f)
        val buff = Tesselator.getInstance().begin(
            VertexFormat.Mode.LINES,
            DefaultVertexFormat.POSITION_COLOR_NORMAL
        )
        buff.addVertex(m, 0f, 0f, 0f).setColor(1f, 1f, 1f, 1f).setNormal(0f, 1f, 0f)
        buff.addVertex(m, 100f, 50f, 0f).setColor(0f, 1f, 1f, 1f).setNormal(0f, 1f, 0f)
        buff.addVertex(m, 100f, 50f, 0f).setColor(0f, 1f, 1f, 1f).setNormal(0f, 1f, 0f)
        buff.addVertex(m, 0f, 100f, 0f).setColor(1f, 1f, 0f, 1f).setNormal(0f, 1f, 0f)

        BufferUploader.drawWithShader(buff.buildOrThrow())
        RenderSystem.depthMask(true)
        RenderSystem.enableCull()
        RenderSystem.enableBlend()

        filter?.arg("Radius", 16)
        filter?.apply(data)
    }

    override fun subElement(id: String): UIElement? = null
    override fun filter(): FilterBase? = filter
}
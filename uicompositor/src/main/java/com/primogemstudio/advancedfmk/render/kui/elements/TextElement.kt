package com.primogemstudio.advancedfmk.render.kui.elements

import com.primogemstudio.advancedfmk.fontengine.BufferManager.renderText
import com.primogemstudio.advancedfmk.fontengine.BufferManager.updateBufferColor
import com.primogemstudio.advancedfmk.fontengine.ComposedFont
import com.primogemstudio.advancedfmk.render.kui.GlobalData
import org.joml.Vector2f
import org.joml.Vector4f

private val FONT = ComposedFont()

class TextElement(
    override var pos: Vector2f,
    var text: String,
    var color: Vector4f,
    var textsize: Int
) : RealElement(pos) {
    override fun render(data: GlobalData) {
        updateBufferColor(0x00ffffff)
        renderText({ vertexConsumer, poseStack ->
            FONT.drawText(
                vertexConsumer,
                poseStack,
                text,
                pos.x.toInt(),
                pos.y.toInt(),
                textsize,
                color,
                240
            )
        }, data.graphics, data.tick)
    }
}
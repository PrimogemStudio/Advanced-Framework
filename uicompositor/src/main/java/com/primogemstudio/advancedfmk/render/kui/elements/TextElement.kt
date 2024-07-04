package com.primogemstudio.advancedfmk.render.kui.elements

import com.primogemstudio.advancedfmk.fontengine.BufferManager.renderText
import com.primogemstudio.advancedfmk.fontengine.BufferManager.updateBufferColor
import com.primogemstudio.advancedfmk.fontengine.ComposedFont
import com.primogemstudio.advancedfmk.render.kui.GlobalData
import net.minecraft.client.Minecraft
import org.joml.Vector2f
import org.joml.Vector4f

private val FONT = ComposedFont()

class TextElement(
    override var pos: Vector2f,
    var text: String,
    var color: Vector4f,
    var textsize: Int,
    var vanilla: Boolean = false
) : RealElement(pos) {
    override fun render(data: GlobalData) {
        val c = (color.x * 255).toInt().and(0xFF).shl(16) +
                (color.y * 255).toInt().and(0xFF).shl(8) +
                (color.z * 255).toInt().and(0xFF)
        if (vanilla) {
            data.graphics.drawString(
                Minecraft.getInstance().font,
                text,
                pos.x.toInt(),
                pos.y.toInt(),
                c + (color.w * 255).toInt().and(0xFF).shl(24)
            )
            return
        }

        updateBufferColor(c)
        renderText({ vertexConsumer, poseStack ->
            FONT.drawText(
                vertexConsumer,
                poseStack,
                text,
                pos.x.toInt(),
                pos.y.toInt(),
                textsize,
                color
            )
        }, data.graphics, data.tick)
    }
}
package com.primogemstudio.advancedfmk.kui.elements

import com.mojang.blaze3d.pipeline.TextureTarget
import com.primogemstudio.advancedfmk.fontengine.BufferManager.renderText
import com.primogemstudio.advancedfmk.fontengine.BufferManager.updateBufferColor
import com.primogemstudio.advancedfmk.fontengine.ComposedFont
import com.primogemstudio.advancedfmk.kui.GlobalData
import net.minecraft.client.Minecraft
import org.joml.Vector2f
import org.joml.Vector4f



class TextElement(
    override var id: String,
    override var pos: Vector2f,
    var text: String,
    var color: Vector4f,
    var textsize: Int,
    var vanilla: Boolean = false
) : RealElement(id, pos) {
    companion object {
        val FONT = ComposedFont()
    }
    var maxLineWidth = -1
    override fun subElement(id: String): UIElement? = null
    override fun renderActual(data: GlobalData) {
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
                color,
                maxLineWidth
            )
        }, data.graphics, data.tick)
    }

    override fun renderWithoutFilter(data: GlobalData) {

    }

    override fun renderWithClip(data: GlobalData, texture: TextureTarget) {
        val c = (color.x * 255).toInt().and(0xFF).shl(16) +
                (color.y * 255).toInt().and(0xFF).shl(8) +
                (color.z * 255).toInt().and(0xFF)

        updateBufferColor(c)
        renderText({ vertexConsumer, poseStack ->
            if (vanilla) data.graphics.drawString(
                Minecraft.getInstance().font,
                text,
                pos.x.toInt(),
                pos.y.toInt(),
                c + (color.w * 255).toInt().and(0xFF).shl(24)
            )
            else FONT.drawText(
                vertexConsumer,
                poseStack,
                text,
                pos.x.toInt(),
                pos.y.toInt(),
                textsize,
                color,
                maxLineWidth
            )
        }, data.graphics, data.tick, texture)
    }
}
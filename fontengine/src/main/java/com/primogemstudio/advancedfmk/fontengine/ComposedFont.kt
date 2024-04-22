package com.primogemstudio.advancedfmk.fontengine

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import org.apache.logging.log4j.LogManager
import org.joml.Vector2f
import kotlin.math.max
import kotlin.text.HexFormat.BytesHexFormat
import kotlin.text.HexFormat.NumberHexFormat

class ComposedFont {
    private val logger = LogManager.getLogger(ComposedFont::class.java)
    private val characterMap = CharacterMap()

    var fontStack = mutableListOf(DefaultFont.FONT)

    init {
        for (c in 0 .. 128 ) {
            fontStack.forEach {
                try {
                    characterMap.put(c.toChar(), it, 50)
                }
                catch (_: Exception) {}
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun loadChar(char: Char): CharGlyph? {
        logger.info("Loading char $char (0x${char.code.toHexString()})")

        fontStack.forEach {
            return try { characterMap.put(char, it, 50) }
            catch (_: Exception) { null }
        }
        return null
    }

    fun drawText(buff: VertexConsumer, poseStack: PoseStack, text: String, x: Int, y: Int, point: Int, textColor: Int) {
        var currOffset = x
        val siz = point.toFloat() / 12f
        text.forEach {
            val glyph = characterMap[it] ?: loadChar(it)
            glyph?: return@forEach
            for (idx in glyph.indices) {
                val v = glyph.vertices[idx]
                poseStack.pushPose()
                buff.vertex(poseStack.last().pose(), v.x * glyph.dimension.x * siz + currOffset, v.y * glyph.dimension.y * siz + y, 0f).color(textColor).endVertex()
                poseStack.popPose()
            }
            currOffset += (glyph.dimension.x * siz).toInt()
        }
    }

    fun drawWrapText(buff: VertexConsumer, poseStack: PoseStack, text: String, x: Int, y: Int, point: Int, maxLineWidth: Int, textColor: Int) {
        var currOffset = x
        val siz = point.toFloat() / 12f
        var currY = y
        var currentLineH = 0
        text.forEach {
            val glyph = characterMap[it] ?: loadChar(it)
            glyph?: return@forEach
            currentLineH = max(currentLineH, (glyph.dimension.y * siz).toInt())
            if (currOffset + (glyph.dimension.x * siz).toInt() - x > maxLineWidth) {
                currY += currentLineH
                currentLineH = 0
                currOffset = x
            }
            for (idx in glyph.indices) {
                val v = glyph.vertices[idx]
                poseStack.pushPose()
                buff.vertex(poseStack.last().pose(), v.x * glyph.dimension.x * siz + currOffset, v.y * glyph.dimension.y * siz + currY, 0f).color(textColor).endVertex()
                poseStack.popPose()
            }
            currOffset += (glyph.dimension.x * siz).toInt()
        }
    }

    fun getTextWidth(text: String, point: Int): Vector2f {
        var currOffset = 0
        val siz = point.toFloat() / 12f
        var currY = 0f

        text.forEach {
            val glyph = characterMap[it] ?: loadChar(it)
            glyph?: return@forEach
            currY = max(currY, glyph.dimension.y * siz)
            currOffset += (glyph.dimension.x * siz).toInt()
        }

        return Vector2f(currOffset.toFloat(), currY)
    }

    fun getWrapTextWidth(text: String, point: Int, maxLineWidth: Int): Vector2f {
        var currOffset = 0
        val siz = point.toFloat() / 12f
        var currY = 0f
        var currentLineH = 0
        text.forEach {
            val glyph = characterMap[it] ?: loadChar(it)
            glyph?: return@forEach
            currentLineH = max(currentLineH, (glyph.dimension.y * siz).toInt())
            if (currOffset + (glyph.dimension.x * siz).toInt() > maxLineWidth) {
                currY += currentLineH
                currentLineH = 0
                currOffset = 0
            }
            currOffset += (glyph.dimension.x * siz).toInt()
        }

        return Vector2f(currOffset.toFloat(), currY)
    }
}

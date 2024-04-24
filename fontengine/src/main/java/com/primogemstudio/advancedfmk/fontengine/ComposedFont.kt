package com.primogemstudio.advancedfmk.fontengine

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import org.apache.logging.log4j.LogManager
import org.joml.Vector2f
import kotlin.math.max

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

    fun fetchGlyphs(text: String): Array<CharGlyph> = text.mapNotNull { characterMap[it] ?: loadChar(it) }.toTypedArray()

    fun drawRect(buff: VertexConsumer, poseStack: PoseStack, text: String, x: Int, y: Int, point: Int, textColor: Int) {
        var currOffset = x
        val siz = point.toFloat() / 12f
        fetchGlyphs(text).forEach {
            for (idx in it.indices) {
                val v = it.vertices[idx]
                poseStack.pushPose()
                buff.vertex(poseStack.last().pose(), v.x * it.dimension.x * siz + currOffset, v.y * it.dimension.y * siz + y, 0f).color(textColor).endVertex()
                poseStack.popPose()
            }
            currOffset += (it.dimension.x * siz * 1.1).toInt()
        }
    }

    fun drawWrapText(buff: VertexConsumer, poseStack: PoseStack, text: String, x: Int, y: Int, point: Int, maxLineWidth: Int, textColor: Int) {
        var currOffset = x
        val siz = point.toFloat() / 12f
        var currY = y
        var currentLineH = 0
        fetchGlyphs(text).forEach {
            currentLineH = max(currentLineH, (it.dimension.y * siz).toInt())
            if (currOffset + (it.dimension.x * siz).toInt() - x > maxLineWidth) {
                currY += currentLineH
                currentLineH = 0
                currOffset = x
            }
            for (idx in it.indices) {
                val v = it.vertices[idx]
                poseStack.pushPose()
                buff.vertex(poseStack.last().pose(), v.x * it.dimension.x * siz + currOffset, v.y * it.dimension.y * siz + currY, 0f).color(textColor).endVertex()
                poseStack.popPose()
            }
            currOffset += (it.dimension.x * siz * 1.1).toInt()
        }
    }

    fun getTextRect(text: String, point: Int): Vector2f {
        var currOffset = 0
        val siz = point.toFloat() / 12f
        var currY = 0f

        fetchGlyphs(text).forEach {
            currY = max(currY, it.dimension.y * siz)
            currOffset += (it.dimension.x * siz * 1.1).toInt()
        }

        return Vector2f(currOffset.toFloat(), currY)
    }

    fun getWrapTextWidth(text: String, point: Int, maxLineWidth: Int): Vector2f {
        var currOffset = 0
        val siz = point.toFloat() / 12f
        var currY = 0f
        var currentLineH = 0
        fetchGlyphs(text).forEach {
            currentLineH = max(currentLineH, (it.dimension.y * siz).toInt())
            if (currOffset + (it.dimension.x * siz).toInt() > maxLineWidth) {
                currY += currentLineH
                currentLineH = 0
                currOffset = 0
            }
            currOffset += (it.dimension.x * siz * 1.1).toInt()
        }

        return Vector2f(currOffset.toFloat(), currY)
    }
}

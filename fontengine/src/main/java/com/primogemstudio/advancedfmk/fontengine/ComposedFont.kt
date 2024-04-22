package com.primogemstudio.advancedfmk.fontengine

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import org.apache.logging.log4j.LogManager

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

    private fun loadChar(char: Char): CharGlyph? {
        logger.info("Loading char $char")

        fontStack.forEach {
            return try { characterMap.put(char, it, 50) }
            catch (_: Exception) { null }
        }
        return null
    }

    fun drawText(buff: VertexConsumer, poseStack: PoseStack, text: String, x: Int, y: Int, point: Int, textColor: Int) {
        var currOffset = x
        val siz = point / 12
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

    fun getTextWidth(text: String, point: Int): Int {
        var currOffset = 0
        val siz = point / 12

        text.forEach {
            val glyph = characterMap[it] ?: loadChar(it)
            glyph?: return@forEach
            currOffset += (glyph.dimension.x * siz).toInt()
        }

        return currOffset
    }
}

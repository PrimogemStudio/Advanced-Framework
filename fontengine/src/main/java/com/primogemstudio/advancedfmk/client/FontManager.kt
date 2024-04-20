package com.primogemstudio.advancedfmk.client

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.primogemstudio.advancedfmk.ftwrap.DefaultFont
import com.primogemstudio.advancedfmk.ftwrap.vtxf.CharGlyph
import com.primogemstudio.advancedfmk.ftwrap.vtxf.CharacterMap
import org.apache.logging.log4j.LogManager

object FontManager {
    private val LOGGER = LogManager.getLogger(FontManager::class.java)
    private val characterMap = CharacterMap()

    var CurrentFont = DefaultFont.FONT

    init {
        for (c in 0 .. 128 ) {
            try {
                characterMap.put(c.toChar(), CurrentFont, 50)
            }
            catch (_: Exception) {}
        }
    }

    private fun loadChar(char: Char): CharGlyph {
        LOGGER.info("Loading char $char")
        characterMap.put(char, CurrentFont, 50)
        return characterMap[char]!!
    }

    fun drawText(buff: VertexConsumer, poseStack: PoseStack, text: String, x: Int, y: Int, textHeight: Int) {
        var currOffset = x
        val siz = textHeight / 12
        text.forEach {
            val glyph = characterMap[it] ?: loadChar(it)
            for (idx in glyph.indices) {
                val v = glyph.vertices[idx]
                poseStack.pushPose()
                buff.vertex(poseStack.last().pose(), v.x * glyph.dimension.x * siz + currOffset, v.y * glyph.dimension.y * siz + y, 0f).color(255, 255, 255, 255).endVertex()
                poseStack.popPose()
            }
            currOffset += (glyph.dimension.x * siz).toInt()
        }
    }
}

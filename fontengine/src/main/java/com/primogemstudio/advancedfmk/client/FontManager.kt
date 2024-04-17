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
        for (c in "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?/[]{};:'\"=-+()!@#$%^&*_<>.,~`") characterMap.put(c, CurrentFont, 50)
    }

    private fun loadChar(char: Char): CharGlyph {
        LOGGER.info("Loading char $char")
        characterMap.put(char, CurrentFont, 50)
        return characterMap[char]!!
    }

    fun drawText(buff: VertexConsumer, poseStack: PoseStack, text: String) {
        var x = 50
        val siz = 20 / 12
        text.forEach {
            val glyph = characterMap[it] ?: loadChar(it)
            for (ind in glyph.indices) {
                val v = glyph.vertices[ind]
                poseStack.pushPose()
                buff.vertex(poseStack.last().pose(), v.x * glyph.dimension.x * siz + x, v.y * glyph.dimension.y * siz + 100, 0f).color(255, 255, 255, 255).endVertex()
                poseStack.popPose()
            }
            x += (glyph.dimension.x * siz).toInt()
        }
    }
}

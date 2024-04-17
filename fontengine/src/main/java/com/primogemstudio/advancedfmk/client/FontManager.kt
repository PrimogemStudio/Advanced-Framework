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
        var x = 3.5
        text.forEach {
            val glyph = characterMap[it] ?: loadChar(it)
            for (ind in glyph.indices) {
                val v = glyph.vertices[ind]
                poseStack.pushPose()
                poseStack.scale(10f, 10f, 1f)
                poseStack.translate(x, 3.5, 0.0)
                buff.vertex(poseStack.last().pose(), v.x, v.y, 0f).color(255, 255, 255, 255).endVertex()
                poseStack.popPose()
            }
            x += glyph.dimension.x / glyph.dimension.y
        }
    }
}

package com.primogemstudio.advancedfmk.client

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.primogemstudio.advancedfmk.ftwrap.DefaultFont
import com.primogemstudio.advancedfmk.ftwrap.vtxf.CharGlyph
import com.primogemstudio.advancedfmk.ftwrap.vtxf.CharacterMap

object FontManager {
    private val characterMap = CharacterMap()

    var CurrentFont = DefaultFont.FONT

    init {
        val preload = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?/[]{};:'\"=-+()!@#$%^&*_<>.,~`"
        for (c in preload) {
            characterMap.put(c, CurrentFont, 15)
        }
    }

    private fun loadChar(char: Char): CharGlyph {
        characterMap.put(char, CurrentFont, 15)
        return characterMap[char]!!
    }

    fun drawText(buff: VertexConsumer, poseStack: PoseStack, text: String) {
        var x = 3.5
        text.forEach {
            val glyph = characterMap[it] ?: loadChar(it)
            for (ind in glyph.indices) {
                val v = glyph.vertices[ind]
                poseStack.pushPose()
                poseStack.scale(30f, 30f, 1f)
                poseStack.translate(x, 3.5, 0.0)
                buff.vertex(poseStack.last().pose(), v.x, v.y, 0f).color(255, 255, 255, 255).endVertex()
                poseStack.popPose()
            }
            x += glyph.dimension.x / glyph.dimension.y
        }
    }
}

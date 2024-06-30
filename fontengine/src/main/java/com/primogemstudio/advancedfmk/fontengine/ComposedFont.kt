package com.primogemstudio.advancedfmk.fontengine

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.primogemstudio.advancedfmk.fontengine.gen.FreeTypeFont
import org.apache.logging.log4j.LogManager
import org.joml.Vector2f
import org.joml.Vector4f
import kotlin.math.max

class ComposedFont {
    private val logger = LogManager.getLogger(javaClass)
    private val characterMap = CharacterMap()

    var fontStack = mutableListOf(
        DefaultFont.DEFAULT_CJK,
        DefaultFont.ARABIC
    )

    init {
        for (c in 0..128) {
            for (it in fontStack) {
                try {
                    characterMap.put(c.toChar(), it, 15)
                    break
                } catch (_: Exception) {}
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun loadChar(char: Char, raw: Boolean = false): CharGlyph? {
        logger.debug("Loading char 0x${char.code.toHexString()}")
        for (it in fontStack) {
            try {
                return characterMap.put(char, it, 15, raw)
            } catch (_: Exception) {}
        }
        return null
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun loadChar(char: Char, font: FreeTypeFont, raw: Boolean = false): CharGlyph? {
        logger.debug("Loading char 0x${char.code.toHexString()} (direct)")
        return characterMap.put(char, font, 15, raw)
    }

    @Suppress("UNCHECKED_CAST")
    fun fetchGlyphs(text: String, shape: Boolean = true): Array<Pair<CharGlyph, Vector2f>> {
        if (shape) {
            var result: Array<Pair<Int, Vector2f>>? = null
            var fnts: Array<FreeTypeFont>? = null
            fontStack.forEach { f ->
                if (result == null) {
                    result = f.shape(text).first
                    fnts = Array(result?.size!!) { f }
                }
                else {
                    val temp = f.shape(text).first
                    for (i in temp.indices) if ((i < result?.size!!) && result?.get(i)?.first == 0) {
                        result?.set(i, temp[i])
                        fnts?.set(i, f)
                    }
                }
            }

            var idx = 0
            return result?.map {
                idx++
                Pair(
                    characterMap[it.first.toChar(), fnts?.get(idx - 1)!!, true] ?: loadChar(
                        it.first.toChar(),
                        fnts?.get(idx - 1)!!,
                        true
                    ),
                    it.second
                )
            }?.filter { it.first != null }?.toTypedArray() as Array<Pair<CharGlyph, Vector2f>>
        } else return text.mapNotNull { characterMap[it, fontStack] ?: loadChar(it) }.map { Pair(it, Vector2f(0f)) }
            .toTypedArray()
    }

    fun drawCenteredText(
        buff: VertexConsumer,
        poseStack: PoseStack,
        text: String,
        x: Int,
        y: Int,
        point: Int,
        maxLineWidth: Int,
        textColor: Vector4f
    ) {
        val rect = getTextBorder(text, point, maxLineWidth)
        drawText(
            buff, poseStack, text, (x - rect.x / 2).toInt(), (y - rect.y / 2).toInt(), point, textColor, maxLineWidth
        )
    }

    fun drawText(
        buff: VertexConsumer,
        poseStack: PoseStack,
        text: String,
        x: Int,
        y: Int,
        point: Int,
        textColor: Vector4f,
        maxLineWidth: Int = -1
    ) {
        var currOffset = x
        val siz = point.toFloat() / 12f
        var currY = y
        var currentLineH = 0
        fetchGlyphs(text).forEach {
            currentLineH = max(currentLineH, (it.first.dimension.y * siz).toInt())
            if (maxLineWidth > 0 && it.second.y == 0f && currOffset + (it.first.dimension.x * siz).toInt() - x > maxLineWidth) {
                currY += currentLineH
                currentLineH = 0
                currOffset = x
            }
            for (idx in it.first.indices) {
                val v = it.first.vertices[idx]
                poseStack.pushPose()
                buff.addVertex(
                    poseStack.last().pose(),
                    (v.x + it.second.x) * it.first.dimension.x * siz + currOffset,
                    (v.y - it.second.y) * it.first.dimension.y * siz + currY,
                    0f
                ).setColor(textColor.x, textColor.y, textColor.z, textColor.w)
                poseStack.popPose()
            }
            if (it.second.y == 0f) currOffset += (it.first.dimension.x * siz).toInt()
        }
    }

    fun getTextBorder(text: String, point: Int, maxLineWidth: Int = -1): Vector2f {
        var currOffset = 0
        val siz = point.toFloat() / 12f
        var currY = 0f
        var currentLineH = 0
        fetchGlyphs(text).forEach {
            currentLineH = max(currentLineH, (it.first.dimension.y * siz).toInt())
            if (maxLineWidth > 0 && currOffset + (it.first.dimension.x * siz).toInt() > maxLineWidth) {
                currY += currentLineH
                currentLineH = 0
                currOffset = 0
            }
            currOffset += (it.first.dimension.x * siz).toInt()
        }

        return Vector2f(currOffset.toFloat(), currY)
    }
}

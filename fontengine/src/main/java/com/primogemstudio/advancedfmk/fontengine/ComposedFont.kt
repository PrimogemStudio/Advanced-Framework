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

    private val shapingCache = mutableMapOf<String, Array<Pair<CharGlyph, Vector2f>>>()

    init {
        for (c in 0..128) {
            for (it in fontStack) {
                try {
                    characterMap.put(c.toChar(), it, 5)
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
                return characterMap.put(char, it, 5, raw)
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
            if (shapingCache.contains(text)) return shapingCache[text]!!
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
            return (result?.map {
                idx++
                Pair(
                    characterMap[it.first.toChar(), fnts?.get(idx - 1)!!, true] ?: loadChar(
                        it.first.toChar(),
                        fnts?.get(idx - 1)!!,
                        true
                    ),
                    it.second
                )
            }?.filter { it.first != null }
                ?.toTypedArray() as Array<Pair<CharGlyph, Vector2f>>).apply { shapingCache[text] = this }
        } else return text.mapNotNull { characterMap[it, fontStack] ?: loadChar(it) }.map { Pair(it, Vector2f(0f)) }
            .toTypedArray()
    }

    fun drawCenteredText(
        buff: VertexConsumer,
        poseStack: PoseStack,
        text: String,
        x: Float,
        y: Float,
        point: Int,
        maxLineWidth: Int,
        textColor: Vector4f
    ) {
        val rect = getTextBorder(text, point, maxLineWidth)
        drawText(
            buff, poseStack, text, x - rect.x / 2, y - rect.y / 2, point, textColor, maxLineWidth
        )
    }

    fun drawText(
        buff: VertexConsumer,
        poseStack: PoseStack,
        text: String,
        x: Float,
        y: Float,
        point: Int,
        textColor: Vector4f,
        maxLineWidth: Int = -1
    ) {
        var currOffset = x
        val siz = point.toFloat() / 8f
        var currY = y + point
        var currentLineH = 0
        poseStack.pushPose()
        fetchGlyphs(text).forEach {
            currentLineH = max(currentLineH, (it.first.dimension.y * siz).toInt())
            if (maxLineWidth > 0 && it.second.y == 0f && currOffset + (it.first.dimension.x * siz).toInt() - x > maxLineWidth) {
                currY += currentLineH
                currentLineH = 0
                currOffset = x
            }
            for (idx in it.first.indices) {
                val v = it.first.vertices[idx]
                buff.addVertex(
                    poseStack.last().pose(),
                    (v.x + it.second.x) * it.first.dimension.x * siz + currOffset,
                    (v.y - it.second.y) * it.first.dimension.y * siz + currY,
                    0f
                ).setColor(textColor.x, textColor.y, textColor.z, textColor.w)
            }
            if (it.second.y == 0f) currOffset += (it.first.dimension.x * siz).toInt()
        }
        poseStack.popPose()
    }

    fun getTextBorder(text: String, point: Int, maxLineWidth: Int = -1): Vector2f {
        var currOffset = 0
        val siz = point.toFloat() / 8f
        var currY = 0f
        var currentLineH = 0
        var maxX = 0
        fetchGlyphs(text).forEach {
            currentLineH = max(currentLineH, (it.first.dimension.y * siz).toInt())
            if (maxLineWidth > 0 && currOffset + (it.first.dimension.x * siz).toInt() > maxLineWidth) {
                maxX = max(maxX, currOffset)
                currY += currentLineH
                currentLineH = 0
                currOffset = 0
            }
            currOffset += (it.first.dimension.x * siz).toInt()
        }

        currY += currentLineH
        maxX = max(maxX, currOffset)
        return Vector2f(maxX.toFloat(), currY)
    }
}

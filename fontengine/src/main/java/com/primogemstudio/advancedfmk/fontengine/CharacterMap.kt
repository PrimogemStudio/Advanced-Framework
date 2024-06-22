package com.primogemstudio.advancedfmk.fontengine

import com.primogemstudio.advancedfmk.fontengine.gen.FreeTypeFont
import org.joml.Vector2f

class CharacterMap {
    private val map = HashMap<String, CharGlyph>()

    operator fun get(char: Char, fontlist: List<FreeTypeFont>, raw: Boolean = false): CharGlyph? {
        return fontlist.map { it.getGlyphName(if (raw) char.code else it.getGlyphId(char.code.toLong())) }.first { it.isNotEmpty() }.let { map[it] }
    }

    fun put(char: Char, font: FreeTypeFont, precision: Int, raw: Boolean = false): CharGlyph? {
        val glyph = font.fetchGlyphOutline(char.code.toLong(), raw) ?: throw RuntimeException("Char not found: $char")
        val id = font.getGlyphName(if (raw) char.code else font.getGlyphId(char.code.toLong()))
        map[id] = glyph.toVertices(precision).bake()
        return map[id]
    }
}

data class CharGlyph(
    val dimension: Vector2f, val vertices: Array<Vector2f>, val indices: IntArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as CharGlyph
        if (dimension != other.dimension) return false
        if (!vertices.contentEquals(other.vertices)) return false
        if (!indices.contentEquals(other.indices)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = dimension.hashCode()
        result = 31 * result + vertices.contentHashCode()
        result = 31 * result + indices.contentHashCode()
        return result
    }
}

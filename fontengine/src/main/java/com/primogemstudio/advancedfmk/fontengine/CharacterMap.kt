package com.primogemstudio.advancedfmk.fontengine

import com.primogemstudio.advancedfmk.fontengine.gen.FreeTypeFont
import org.joml.Vector2f

class CharacterMap {
    private val map = HashMap<Char, CharGlyph>()

    operator fun get(char: Char): CharGlyph? {
        return map[char]
    }

    fun put(char: Char, font: FreeTypeFont, precision: Int) {
        val glyph = font.fetchGlyphOutline(char.code.toLong()) ?: throw RuntimeException("Char not found: $char")
        map[char] = glyph.toVertices(precision).bake()
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

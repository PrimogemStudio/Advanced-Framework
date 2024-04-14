package com.primogemstudio.advancedfmk.ftwrap

import org.joml.Vector2f

data class FreeTypeGlyph(
    val whscale: Float, val vertices: Array<Vector2f>, val indices: IntArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FreeTypeGlyph

        if (whscale != other.whscale) return false
        if (!vertices.contentEquals(other.vertices)) return false
        if (!indices.contentEquals(other.indices)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = whscale.hashCode()
        result = 31 * result + vertices.contentHashCode()
        result = 31 * result + indices.contentHashCode()
        return result
    }
}
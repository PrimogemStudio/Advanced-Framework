package com.primogemstudio.advancedfmk.ftwrap

import org.joml.Vector2f

data class Polygon(
    val vertices: MutableList<Vector2f>, val holes: MutableList<Polygon> = mutableListOf()
) {
    fun toTriangles(): List<Array<Vector2f>> {
        val r = mutableListOf<Array<Vector2f>>()

        var holeSize = holes.sumOf { it.vertices.size }
        val base = vertices.size - holeSize - 1
        val arr = mutableListOf(base)
        for (i in 0..<holes.size - 1) {
            holeSize -= holes[i].vertices.size
            arr.add(vertices.size - holeSize - 0)
        }

        val re = EarCut.earcut(
            vertices.flatMap { listOf(it.x, it.y) }.toFloatArray(),
            if (holes.isNotEmpty()) arr.toIntArray() else IntArray(0),
            2
        )
        for (i in 0..<re.size / 3) {
            r.add(arrayOf(vertices[re[i * 3]], vertices[re[i * 3 + 1]], vertices[re[i * 3 + 2]]))
        }

        return r
    }
}
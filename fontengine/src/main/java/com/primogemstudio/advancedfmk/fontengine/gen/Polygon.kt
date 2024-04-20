package com.primogemstudio.advancedfmk.fontengine.gen

import org.joml.Vector2f

data class Polygon(
    val vertices: MutableList<Vector2f>, val holes: MutableList<Polygon> = mutableListOf()
) {
    fun toTriangles(): List<Int> {
        val r = mutableListOf<Int>()

        var holeSize = holes.sumOf { it.vertices.size }
        val base = vertices.size - holeSize - 1
        val arr = mutableListOf(base)
        for (i in 0..<holes.size - 1) {
            holeSize -= holes[i].vertices.size
            arr.add(vertices.size - holeSize - 0)
        }

        val re = EarCutTriangulation.earcut(
            vertices.flatMap { listOf(it.x, it.y) }.toFloatArray(),
            if (holes.isNotEmpty()) arr.toIntArray() else IntArray(0),
            2
        )
        for (i in 0..<re.size / 3) {
            r.addAll(arrayOf(re[i * 3], re[i * 3 + 1], re[i * 3 + 2]))
        }

        return r
    }
}
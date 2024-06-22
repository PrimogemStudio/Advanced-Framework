package com.primogemstudio.advancedfmk.fontengine.gen

import org.joml.Vector2f
import kotlin.math.abs

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
            vertices.flatMap { listOf(it.x.toDouble(), it.y.toDouble()) }.toDoubleArray(),
            if (holes.isNotEmpty()) arr.toIntArray() else IntArray(0),
            2
        )
        for (i in 0..<re.size / 3) {
            r.addAll(arrayOf(re[i * 3], re[i * 3 + 1], re[i * 3 + 2]))
        }

        return r
    }

    fun area(): Float {
        var j: Int
        var area = 0f
        for (i in vertices.indices) {
            j = (i + 1) % vertices.size
            area += vertices[i].x * vertices[j].y
            area -= vertices[i].y * vertices[j].x
        }
        area /= 2
        return abs(area)
    }

    fun contains(other: Polygon): Boolean {
        var contains = true
        for (point in other.vertices) {
            if (!isInPoly(point, this)) {
                contains = false
                break
            }
        }

        return contains
    }

    private fun isInPoly(point: Vector2f, polygon: Polygon): Boolean {
        var count = 0
        for (i in polygon.vertices.indices) {
            val p1 = polygon.vertices[i]
            val p2 = polygon.vertices[(i + 1) % polygon.vertices.size]
            if ((p1.y > point.y) != (p2.y > point.y) && (point.x < (p2.x - p1.x) * (point.y - p1.y) / (p2.y - p1.y) + p1.x)) count++
        }

        return count % 2 == 1
    }
}
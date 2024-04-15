package com.primogemstudio.advancedfmk.ftwrap

import com.primogemstudio.advancedfmk.ftwrap.vtxf.CharGlyph
import com.primogemstudio.advancedfmk.util.conic
import com.primogemstudio.advancedfmk.util.cubic
import org.joml.Vector2f
import java.util.*

class SVGQueue(private val dimension: Vector2f) : Vector<SVGOperation>() {
    fun toVertices(precision: Int): MultiPolygon {
        fun MutableList<Vector2f>.addN(d: Vector2f) {
            if (isEmpty()) add(d)
            if (get(size - 1) != d) add(d)
        }

        val polygons = MultiPolygon(dimension)

        val vertices = mutableListOf<Vector2f>()
        val spl = {
            polygons.add(Polygon(ArrayList(vertices)))
            vertices.clear()
        }
        var trig = false
        forEach {
            when (it.type) {
                SVGOperation.OpType.MOVE -> {
                    if (trig) spl()
                    else trig = true

                    vertices.addN(it.target)
                }

                SVGOperation.OpType.LINE -> vertices.addN(it.target)
                SVGOperation.OpType.CONIC -> {
                    val pos = vertices[vertices.size - 1]
                    for (i in 0 until precision) {
                        vertices.addN(conic(pos, it.control1!!, it.target, i.toFloat() / precision))
                    }
                }

                SVGOperation.OpType.CUBIC -> {
                    val pos = vertices[vertices.size - 1]
                    for (i in 0 until precision) {
                        vertices.addN(cubic(pos, it.control1!!, it.control2!!, it.target, i.toFloat() / precision))
                    }
                }
            }
        }
        spl()

        val contained = mutableListOf<Pair<Polygon, Polygon>>()

        for (i in polygons) {
            for (j in polygons) {
                if (i != j) {
                    var contains = true
                    for (point in j.vertices) {
                        if (!isInPoly(point, i)) {
                            contains = false
                            break
                        }
                    }

                    if (contains) contained.add(Pair(i, j))
                }
            }
        }

        contained.forEach { (t, u) ->
            t.vertices.addAll(u.vertices)
            polygons.remove(u)
            t.holes.add(u)
        }

        return polygons
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

class MultiPolygon(private val dimension: Vector2f) : Vector<Polygon>() {
    fun bake(): CharGlyph {
        val vertices = mutableListOf<Vector2f>()
        val indices = mutableListOf<Int>()
        var base = 0
        forEach {
            vertices.addAll(it.vertices)
            indices.addAll(it.toTriangles().map { it + base })
            base = vertices.size
        }
        vertices.forEach { it.x *= dimension.x / dimension.y }
        return CharGlyph(dimension, vertices.toTypedArray(), indices.toIntArray())
    }
}
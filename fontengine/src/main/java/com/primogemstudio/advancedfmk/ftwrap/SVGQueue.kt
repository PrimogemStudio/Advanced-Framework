package com.primogemstudio.advancedfmk.ftwrap

import com.primogemstudio.advancedfmk.util.conic
import com.primogemstudio.advancedfmk.util.cubic
import org.joml.Vector2f
import java.util.*

class SVGQueue : Vector<SVGOperation>() {
    fun split(precision: Int): List<Polygon> {
        fun MutableList<Vector2f>.addN(d: Vector2f) {
            if (isEmpty()) add(d)
            if (get(size - 1) != d) add(d)
        }

        val polygons = mutableListOf<Polygon>()

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
        }

        return polygons
    }

    fun isInPoly(point: Vector2f, polygon: Polygon): Boolean {
        var count = 0
        for (i in polygon.vertices.indices) {
            val p1 = polygon.vertices[i]
            val p2 = polygon.vertices[(i + 1) % polygon.vertices.size]
            if ((p1.y > point.y) != (p2.y > point.y) && (point.x < (p2.x - p1.x) * (point.y - p1.y) / (p2.y - p1.y) + p1.x)) count++
        }

        return count % 2 == 1
    }
}



/*
count = 0
    for i in range(len(polygon)):
        p1 = polygon[i]
        p2 = polygon[(i + 1) % len(polygon)]
        if ((p1.y > point.y) != (p2.y > point.y)) and \
                (point.x < (p2.x - p1.x) * (point.y - p1.y) / (p2.y - p1.y) + p1.x):
            count += 1
    return count % 2 == 1*/
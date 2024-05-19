package com.primogemstudio.advancedfmk.fontengine.gen

import com.primogemstudio.advancedfmk.fontengine.CharGlyph
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

        val areas = polygons.map { Pair(it, it.area()) }.sortedBy { it.second }.reversed().map { it.first }.toMutableList()
        val infos = mutableListOf<PolygonInfo>()
        var currentDepth = 0

        while (areas.isNotEmpty()) {
            for (i in areas) {
                var isTop = true
                for (j in areas) {
                    if (i != j && j.contains(i)) {
                        isTop = false
                    }
                }
                if (isTop) infos.add(infos.filter { it.depth == currentDepth - 1 }.filter { it.poly.contains(i) }.firstOrNull()?.bindNew(i)?: PolygonInfo(i, 0, null))
            }
            areas.removeIf { infos.map { p -> p.poly }.contains(it) }
            currentDepth ++
        }
        val polygonsl = infos.filter { it.depth % 2 == 0 }.map { pi ->
            infos.filter { it.depth == pi.depth + 1 && it.parent == pi.poly }.map { it.poly }.forEach {
                pi.poly.vertices.addAll(it.vertices)
                pi.poly.holes.add(it)
            }
            pi.poly
        }
        polygons.removeIf { true }
        polygons.addAll(polygonsl)

        return polygons
    }
}

data class PolygonInfo(
    val poly: Polygon,
    val depth: Int,
    val parent: Polygon?
) {
    fun bindNew(p: Polygon): PolygonInfo = PolygonInfo(p, depth + 1, poly)
}

class MultiPolygon(private val dimension: Vector2f) : Vector<Polygon>() {
    fun bake(): CharGlyph {
        val vertices = mutableListOf<Vector2f>()
        val indices = mutableListOf<Int>()
        var base = 0
        forEach {
            vertices.addAll(it.vertices.map { it.div(16f) })
            indices.addAll(it.toTriangles().map { it + base })
            base = vertices.size
        }

        return CharGlyph(dimension, vertices.toTypedArray(), indices.toIntArray())
    }
}
package com.primogemstudio.advancedfmk.ftwrap

import com.primogemstudio.advancedfmk.util.conic
import com.primogemstudio.advancedfmk.util.cubic
import org.joml.Vector2f
import java.util.Vector

class SVGQueue: Vector<SVGOperation>() {
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
                    for (i in 0 ..< precision) {
                        vertices.addN(conic(pos, it.control1!!, it.target, i.toFloat() / precision))
                    }
                }
                SVGOperation.OpType.CUBIC -> {
                    val pos = vertices[vertices.size - 1]
                    for (i in 0 ..< precision) {
                        vertices.addN(cubic(pos, it.control1!!, it.control2!!, it.target, i.toFloat() / precision))
                    }
                }
            }
        }
        spl()

        return polygons
    }
}
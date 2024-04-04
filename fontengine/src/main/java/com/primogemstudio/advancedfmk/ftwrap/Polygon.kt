package com.primogemstudio.advancedfmk.ftwrap

import org.joml.Vector2f
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Polygon(
    val vertices: List<Vector2f>
) {
    fun toTriangles(): List<Array<Vector2f>> {
        val r = mutableListOf<Array<Vector2f>>()
        val cpy = Vector(vertices)
        var s = vertices.size
        if (vertices.size <= 4) {
            r.add(vertices.toTypedArray())
            return r
        }

        var i = 0
        var j = 1
        var k = 2
        while (s > 4) {
            val a1 = cpy[j].x - cpy[i].x
            val a2 = cpy[j].y - cpy[i].y
            val b1 = cpy[k].x - cpy[i].x
            val b2 = cpy[k].y - cpy[i].y
            val cond1 = a1*b2 <= a2*b1

            var cond2 = true

            for (p in 0 ..< s) {
                if (p != i && p != j && p != k) {
                    if (inTri(cpy[i], cpy[j], cpy[k], cpy[p])) {
                        cond2 = false
                        break
                    }
                }
            }

            if (cond1 && cond2) {
                r.add(arrayOf(cpy[i], cpy[j], cpy[k]))
                for (t in j ..< s - 1) {
                    cpy[t] = cpy[t + 1]
                }

                s--
                i = 0
                j = 1
                k = 2
            }
            else {
                i++
                j = i + 1
                k = j + 1
                if (k > s - 1) {
                    r.add(arrayOf(cpy[i - 1], cpy[j - 1], cpy[k - 1]))
                    cpy.removeAt(j - 1)

                    s--
                    i = 0
                    j = 1
                    k = 2
                }
            }
        }

        return r
    }
}

fun inTri(a: Vector2f, b: Vector2f, c: Vector2f, p: Vector2f): Boolean = getArea(a, b, c) >= getArea(a, b, p) + getArea(a, c, p) + getArea(b, c, p)
fun getArea(a: Vector2f, b: Vector2f, c: Vector2f): Float {
    val A = getLength(a, b)
    val B = getLength(a, c)
    val C = getLength(b, c)
    val p = (A + B + C) / 2
    return sqrt(p * (p - A) * (p - B) * (p - C))
}
fun getLength(a: Vector2f, b: Vector2f): Float = sqrt(abs(b.x - a.x).pow(2) + abs(b.y - a.y).pow(2))
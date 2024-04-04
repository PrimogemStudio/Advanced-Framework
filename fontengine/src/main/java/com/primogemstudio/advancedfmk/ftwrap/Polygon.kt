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
        val remain = Vector(vertices)

        var p0 = remain[0]
        var p1 = remain[1]
        var p2 = remain[2]

        val sl = {
            p0 = p1
            p1 = p2
            p2 = vertices[vertices.indexOf(p2) + 1]
        }

        while (remain.size > 3) {
            if (isLeft(p0, p1, p2)) {
                sl()
                continue
            }
            else {
                var inned = false
                for (i in remain) {
                    if (p0 != i && p1 != i && p2 != i && inTri(p0, p1, p2, i)) {
                        sl()
                        inned = true
                    }
                }

                if (inned) {
                    sl()
                    continue
                }
                else {
                    r.add(arrayOf(p0, p1, p2))
                    remain.removeAll(arrayOf(p0, p1, p2).toSet())

                    p0 = remain[0]
                    p1 = remain[1]
                    p2 = remain[2]
                }
            }
        }

        return r
    }
}

fun isLeft(a: Vector2f, b: Vector2f, c: Vector2f): Boolean {
    val k = (b[1] - a[1]) / (b[0] - a[0])
    val b = a[1] - k * a[0]

    val yc = k * c[0] + b
    if (yc < c[1]) return true
    return false
}

fun inTri(a: Vector2f, b: Vector2f, c: Vector2f, p: Vector2f): Boolean = getArea(a, b, c) == getArea(a, b, p) + getArea(a, c, p) + getArea(b, c, p)
fun getArea(a: Vector2f, b: Vector2f, c: Vector2f): Float {
    val A = getLength(a, b)
    val B = getLength(a, c)
    val C = getLength(b, c)
    val p = (A + B + C) / 2
    return sqrt(p * (p - A) * (p - B) * (p - C))
}
fun getLength(a: Vector2f, b: Vector2f): Float = sqrt(abs(b.x - a.x).pow(2) + abs(b.y - a.y).pow(2))
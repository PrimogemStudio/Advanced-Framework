package com.primogemstudio.advancedfmk.ftwrap

import org.joml.Vector2f
import kotlin.math.pow

typealias Vec2 = Vector2f

fun conic(st: Vec2, ct: Vec2, end: Vec2, add: Float): Vec2 {
    val s = Vector2f(st.x, st.y)
    val c = Vector2f(ct.x, ct.y)
    val e = Vector2f(end.x, end.y)
    return s.mul((1 - add).pow(2)).add(c.mul(2 * add * (1 - add))).add(e.mul(add.pow(2)))
}
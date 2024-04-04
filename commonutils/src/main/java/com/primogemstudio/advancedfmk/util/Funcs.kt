package com.primogemstudio.advancedfmk.util

import org.joml.Vector2f
import kotlin.math.pow

typealias Vec2 = Vector2f

fun conic(st: Vec2, ct: Vec2, end: Vec2, add: Float): Vec2 {
    val s = Vec2(st)
    val c = Vec2(ct)
    val e = Vec2(end)
    return s.mul((1 - add).pow(2)).add(c.mul(2 * add * (1 - add))).add(e.mul(add.pow(2)))
}

fun cubic(st: Vec2, ct1: Vec2, ct2: Vec2, end: Vec2, add: Float): Vec2 {
    val a = Vec2(st)
    val b = Vec2(ct1)
    val c = Vec2(ct2)
    val d = Vec2(end)
    return a.mul((1 - add).pow(3)).add(b.mul(3 * add * (1 - add).pow(2))).add(c.mul(3 * add.pow(2) * (1 - add))).add(d.mul(add.pow(3)))
}

fun i26p6tof(i: Int): Float {
    return i.toFloat() * (2.0.pow(-6).toFloat())
}
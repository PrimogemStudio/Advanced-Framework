package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.render.uiframework.animation.*

fun main() {
    for (i in 0 .. 100) {
        println("${i.toDouble() / 100.0} -> ${QuadraticInOut.gen(i.toDouble() / 100.0)}")
    }
}
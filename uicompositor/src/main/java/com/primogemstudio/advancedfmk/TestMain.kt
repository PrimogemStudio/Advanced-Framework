package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.render.uiframework.animation.*

fun main() {
    listOf(Linear, QuadraticIn, QuadraticOut, QuadraticInOut, SinusoidalIn, SinusoidalOut, SinusoidalInOut, ExponentialIn, ExponentialOut, ExponentialInOut, CircularIn, CircularOut, CircularInOut).forEach {
        println(it::class)

        for (i in 0 .. 100) {
            println("${i.toDouble() / 100.0} -> ${it.gen(i.toDouble() / 100.0)}")
        }
    }
}
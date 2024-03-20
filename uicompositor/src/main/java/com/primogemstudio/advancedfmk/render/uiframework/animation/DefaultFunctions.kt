package com.primogemstudio.advancedfmk.render.uiframework.animation

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

typealias DG = DataGenerator

val Linear: DG = DG { it }
val QuadraticIn: DG = DG { it * it }
val QuadraticOut: DG = DG { - it * (it - 2) }
val QuadraticInOut: DG = DG {
    val i1 = it * 2
    if (i1 < 1) return@DG i1 * i1
    val i2 = i1 - 1
    -(i2 * (i2 - 2) - 1) / 2
}

val SinusoidalIn: DG = DG { 1 - cos(it * PI / 2) }
val SinusoidalOut: DG = DG { sin(it * PI / 2) }
val SinusoidalInOut: DG = DG { -(cos(PI * it) - 1) / 2 }

val ExponentialIn: DG = DG {
    if (it == 0.0) return@DG 0.0
    2.0.pow(10 * (it - 1))
}
val ExponentialOut: DG = DG {
    if (it == 1.0) return@DG 1.0
    1 - 2.0.pow(-10 * it)
}
val ExponentialInOut: DG = DG {
    if (it == 0.0) return@DG 0.0
    if (it == 1.0) return@DG 1.0
    val i1 = it * 2
    if (i1 < 1) return@DG 2.0.pow(10 * (i1 - 1)) / 2
    (2 - 2.0.pow(-10 * it)) / 2
}
package com.primogemstudio.advancedfmk.render.uiframework.animation

import kotlin.math.*

typealias DG = DataGenerator

val Linear: DG = DG { it }
val QuadraticIn: DG = DG { it * it }
val QuadraticOut: DG = DG { - it * (it - 2) }
val QuadraticInOut: DG = DG {
    val i1 = it * 2
    if (i1 < 1) return@DG i1 * i1
    val i2 = i1 - 1
    -(i2 * (i2 - 2) - 1) * 0.5
}

val SinusoidalIn: DG = DG { 1 - cos(it * PI * 0.5) }
val SinusoidalOut: DG = DG { sin(it * PI * 0.5) }
val SinusoidalInOut: DG = DG { -(cos(PI * it) - 1) * 0.5 }

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
    if (i1 < 1) return@DG 2.0.pow(10 * (i1 - 1)) * 0.5
    (2 - 2.0.pow(-10 * it)) * 0.5
}

val CircularIn: DG = DG { -(sqrt(1 - it * it) - 1) }
val CircularOut: DG = DG {
    val i1 = it - 1
    sqrt(1 - i1 * i1)
}
val CircularInOut: DG = DG {
    val i1 = it * 2
    if (i1 < 1) return@DG -(sqrt(1 - i1 * i1) - 1) * 0.5
    val i2 = i1 - 2
    (sqrt(1 - i2 * i2) + 1) * 0.5
}

val CubicIn: DG = DG { it * it * it }
val CubicOut: DG = DG {
    val i1 = it - 1
    i1 * i1 * i1 + 1
}
val CubicInOut: DG = DG {
    val i1 = it * 2
    if (i1 < 1) return@DG i1 * i1 * i1 / 2
    val i2 = i1 - 2
    i2 * i2 * i2 / 2 + 1
}

val QuarticIn: DG = DG { it * it * it * it }
val QuarticOut: DG = DG {
    val i1 = it - 1
    1 - i1 * i1 * i1 * i1
}
val QuarticInOut: DG = DG {
    val i1 = it * 2
    if (i1 < 1) return@DG i1 * i1 * i1 * i1 / 2
    val i2 = i1 - 2
    1 - i2 * i2 * i2 * i2 / 2
}

val QuinticIn: DG = DG { it * it * it * it * it }
val QuinticOut: DG = DG {
    val i1 = it - 1
    i1 * i1 * i1 * i1 * i1 + 1
}
val QuinticInOut: DG = DG {
    val i1 = it * 2
    if (i1 < 1) return@DG i1 * i1 * i1 * i1 * i1 / 2
    val i2 = i1 - 2
    i2 * i2 * i2 * i2 * i2 / 2 + 1
}
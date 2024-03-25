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

val ElasticIn: DG = DG {
    if (it == 0.0) return@DG 0.0
    if (it == 1.0) return@DG 1.0
    val c4: Double = (2 * Math.PI) / 3
    -(2.0.pow(10 * it - 10)) * sin((it * 10 - 10.75) * c4)
}
val ElasticOut: DG = DG {
    if (it == 0.0) return@DG 0.0
    if (it == 1.0) return@DG 1.0
    val c4: Double = (2 * Math.PI) / 3
    2.0.pow(-10 * it) * sin((it * 10 - 0.75) * c4) + 1
}
val ElasticInOut: DG = DG {
    val c5: Double = (2 * Math.PI) / 4.5
    if (it == 0.0) return@DG 0.0
    if (it == 1.0) return@DG 1.0
    if (it < 0.5) return@DG -(2.0.pow(20 * it - 10) * sin((20 * it - 11.125) * c5)) / 2
    (2.0.pow(-20 * it + 10) * sin((20 * it - 11.125) * c5)) / 2 + 1
}

val BackIn: DG = DG {
    val s = 1.70158
    it * it * ((s + 1) * it - s)
}
val BackOut: DG = DG {
    val s = 1.70158
    val i1: Double = it - 1
    (i1 * i1 * ((s + 1) * i1 + s) + 1)
}
val BackInOut: DG = DG {
    val s = 1.70158
    val i1: Double = it * 2
    val i2 = s * 1.525
    if (i1 < 1) return@DG (i1 * i1 * ((i2 + 1) * i1 - i2)) / 2

    val i3 = i1 - 2
    val i4 = s * 1.525
    (i3 * i3 * ((i4 + 1) * i3 + i4) + 2) / 2
}

val BounceIn: DG = DG { 1.0 - BounceOut.gen(1.0 - it) }
val BounceOut: DG = DG {
    var i1 = it
    if (i1 < 1 / 2.75) return@DG (7.5625 * i1 * i1)
    if (i1 < 2 / 2.75) {
        i1 -= 1.5 / 2.75
        return@DG 7.5625 * i1 * i1 + 0.75
    }
    if (i1 < 2.5 / 2.75) {
        i1 -= 2.25 / 2.75
        return@DG 7.5625 * i1 * i1 + 0.9375
    }
    i1 -= 2.625 / 2.75
    7.5625 * i1 * i1 + 0.984375
}
val BounceInOut: DG = DG {
    if (it < 0.5) return@DG BounceIn.gen(it * 2) * 0.5
    BounceOut.gen(it * 2 - 1) * 0.5 + 0.5
}
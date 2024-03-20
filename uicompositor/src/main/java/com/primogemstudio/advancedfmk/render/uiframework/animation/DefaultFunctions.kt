package com.primogemstudio.advancedfmk.render.uiframework.animation

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

val Linear: DataGenerator = DataGenerator { it }
val QuadraticIn: DataGenerator = DataGenerator { it * it }
val QuadraticOut: DataGenerator = DataGenerator { - it * (it - 2) }
val QuadraticInOut: DataGenerator = DataGenerator {
    val i1 = it * 2
    if (i1 < 1) return@DataGenerator i1 * i1
    val i2 = i1 - 1
    -(i2 * (i2 - 2) - 1) / 2
}

val SinusoidalIn: DataGenerator = DataGenerator { 1 - cos(it * PI / 2) }
val SinusoidalOut: DataGenerator = DataGenerator { sin(it * PI / 2) }
val SinusoidalInOut: DataGenerator = DataGenerator { -(cos(PI * it) - 1) / 2 }

val ExponentialIn: DataGenerator = DataGenerator {
    if (it == 0.0) return@DataGenerator 0.0
    2.0.pow(10 * (it - 1))
}
val ExponentialOut: DataGenerator = DataGenerator {
    if (it == 1.0) return@DataGenerator 1.0
    1 - 2.0.pow(-10 * it)
}
val ExponentialInOut: DataGenerator = DataGenerator {
    if (it == 0.0) return@DataGenerator 0.0
    if (it == 1.0) return@DataGenerator 1.0
    val i1 = it * 2
    if (i1 < 1) return@DataGenerator 2.0.pow(10 * (i1 - 1)) / 2
    (2 - 2.0.pow(-10 * it)) / 2
}
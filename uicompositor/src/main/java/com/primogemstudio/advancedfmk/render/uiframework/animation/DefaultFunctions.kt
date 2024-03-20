package com.primogemstudio.advancedfmk.render.uiframework.animation

val Linear: DataGenerator = DataGenerator { it }
val QuadraticIn: DataGenerator = DataGenerator { it * it }
val QuadraticOut: DataGenerator = DataGenerator { - it * (it - 2) }
val QuadraticInOut: DataGenerator = DataGenerator {
    /*double i1 = t / d * 2;
    if (i1 < 1) return c / 2 * i1 * i1 + b;
    double i2 = i1 - 1;
    return -c / 2 * (i2 * (i2 - 2) - 1) + b;*/
    val i1 = it * 2
    if (i1 < 1) it * it / 2
    val i2 = i1 - 1
    -(i2 * (i2 - 2) - 1) / 2
}
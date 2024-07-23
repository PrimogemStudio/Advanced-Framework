package com.primogemstudio.advancedfmk.kui.animation

import kotlin.math.min

open class AnimationEvent<T: Number>(
    var timestamp: Long,
    val duration: Long,
    var start: T,
    var target: T,
    val interprocess: DataGenerator,
    val func: (T) -> Unit
) {
    var onEventTrigger: (AnimationEvent<T>) -> Unit = {}
    @Suppress("UNCHECKED_CAST")
    open fun update() {
        if (duration <= 0) return
        onEventTrigger(this)

        val s = start
        val t = target

        val prov = interprocess.gen(min(duration, System.currentTimeMillis() - timestamp).toDouble() / duration)
        if (s is Long && t is Long) func((s + (t - s) * prov).toLong() as T)
        if (s is Int && t is Int) func((s + (t - s) * prov).toInt() as T)
        if (s is Short && t is Short) func((s + (t - s) * prov).toInt().toShort() as T)
        if (s is Float && t is Float) func((s + (t - s) * prov).toFloat() as T)
        if (s is Double && t is Double) func((s + (t - s) * prov) as T)
    }

    fun finish(): Boolean = System.currentTimeMillis() - timestamp > duration
    fun reset() { timestamp = System.currentTimeMillis() }
    fun finished(): Long = System.currentTimeMillis() - timestamp - duration
}
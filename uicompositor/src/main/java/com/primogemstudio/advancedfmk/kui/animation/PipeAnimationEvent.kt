package com.primogemstudio.advancedfmk.kui.animation

@Suppress("UNCHECKED_CAST")
class PipeAnimationEvent<T: Number>(
    val appl: (T) -> Unit
): AnimationEvent<T>(0, 0, 0 as T, 0 as T, Linear, appl) {
    var source: () -> T? = { null }
    override fun update() {
        onEventTrigger(this)
        appl(source()!!)
    }
}
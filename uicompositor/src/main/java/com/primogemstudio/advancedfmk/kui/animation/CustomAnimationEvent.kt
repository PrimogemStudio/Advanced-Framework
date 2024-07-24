package com.primogemstudio.advancedfmk.kui.animation

@Suppress("UNCHECKED_CAST")
open class CustomAnimationEvent<T: Number>(open val updateFunc: () -> Unit):
    AnimationEvent<T>(0, 0, 0 as T, 0 as T, Linear, {}) {
    override fun update() = updateFunc()
}
package com.primogemstudio.advancedfmk.kui.animation

class TimedEvent<T: Number>(val interval: Long, override val updateFunc: () -> Unit): CustomAnimationEvent<T>(updateFunc) {
    private var ts = System.currentTimeMillis()
    override fun update() {
        if (System.currentTimeMillis() - ts >= interval) {
            super.update()
            ts = System.currentTimeMillis()
        }
    }
}
package com.primogemstudio.advancedfmk.kui.animation

class TimedEvent<T: Number>(val interval: Long, override val updateFunc: () -> Unit): CustomAnimationEvent<T>(updateFunc) {
    private var ts = System.currentTimeMillis()
    var durationFetch: (Long) -> Unit = {}
    override fun update() {
        durationFetch(System.currentTimeMillis() - ts)
        if (System.currentTimeMillis() - ts >= interval) {
            super.update()
            ts = System.currentTimeMillis()
        }
    }
}
package com.primogemstudio.advancedfmk.simulator

abstract class Simulator(open val func: (ContextWrapper) -> ResultWrapper) {
    fun loopMain() {
        var result: ResultWrapper? = null
        while (result?.finished != true) {
            val context = ContextWrapper(
                simulator = this@Simulator
            )
            simulateStep(context)
            result = func(context)
        }
    }

    abstract fun simulateStep(context: ContextWrapper)
}
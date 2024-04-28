package com.primogemstudio.advancedfmk.simulator

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

abstract class Simulator(open val func: (ContextWrapper) -> ResultWrapper): Cloneable {
    @OptIn(DelicateCoroutinesApi::class)
    fun loopMain(): Deferred<Unit> {
        return GlobalScope.async {
            var result: ResultWrapper? = null
            while (result?.finished != true) {
                result = func(simulateStep())
            }
        }
    }

    fun simulateStep(): ContextWrapper = ContextWrapper(
        simulator = this
    ).apply { simulateStep(this) }
    abstract fun simulateStep(context: ContextWrapper)
    abstract override fun clone(): Simulator
}
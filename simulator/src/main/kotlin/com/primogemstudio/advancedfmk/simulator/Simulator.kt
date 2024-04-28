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
                val context = ContextWrapper(
                    simulator = this@Simulator
                )
                simulateStep(context)
                result = func(context)
            }
        }
    }

    abstract fun simulateStep(context: ContextWrapper)
    abstract override fun clone(): Simulator
}
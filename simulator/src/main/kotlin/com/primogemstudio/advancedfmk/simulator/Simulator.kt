package com.primogemstudio.advancedfmk.simulator

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class Simulator(val func: (ContextWrapper) -> ResultWrapper) {
    @OptIn(DelicateCoroutinesApi::class)
    fun loopMain(): Job {
        return GlobalScope.launch {
            var result: ResultWrapper? = null
            while (result?.finished != false) {
                val context = ContextWrapper(
                    simulator = this@Simulator
                )
                simulateStep(context)
                result = func(context)
            }
        }
    }

    abstract fun simulateStep(context: ContextWrapper)
}
package com.primogemstudio.advancedfmk.simulator

class Simulator(val enable: (ContextWrapper) -> Boolean) {
    fun loopMain() {
        while (enable(ContextWrapper(
            simulator = this
        ))) {

        }
    }
}
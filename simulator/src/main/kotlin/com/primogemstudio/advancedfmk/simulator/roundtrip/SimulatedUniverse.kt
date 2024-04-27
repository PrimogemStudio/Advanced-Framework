package com.primogemstudio.advancedfmk.simulator.roundtrip

import com.primogemstudio.advancedfmk.simulator.ContextWrapper
import com.primogemstudio.advancedfmk.simulator.ResultWrapper
import com.primogemstudio.advancedfmk.simulator.Simulator
import java.util.*

class SimulatedUniverse(
    val characters: MutableList<CharacterBase> = mutableListOf(),
    val enemies: MutableList<CharacterBase> = mutableListOf(),
    val funcRequestTarget: (TargetRequestContextWrapper) -> Int
): Simulator({
    ResultWrapper(
        characters.map { it.calcHealth() }.sum() == 0f ||
                enemies.map { it.calcHealth() }.sum() == 0f
    )
}) {
    private val operateQueue: Deque<MutableList<CharacterBase>> = LinkedList()
    override fun simulateStep(context: ContextWrapper) {
        if (operateQueue.isEmpty()) {
            characters.forEach { operateQueue.add(mutableListOf(it)) }
            enemies.forEach { operateQueue.add(mutableListOf(it)) }
        }

        val cu = operateQueue.poll()
        while (cu.size > 0) {
            val current = cu[0]
            when (current.type()) {
                CharacterBase.Type.Controllable -> enemies
                CharacterBase.Type.UnControllable -> characters
            }.apply {
                this[funcRequestTarget(
                    TargetRequestContextWrapper(
                    this@SimulatedUniverse, current, this
                )
                )].operateHealth { it.setter.call(it.getter.call() - current.calcOutputMain()) }
            }
            cu.removeAt(0)
        }
    }
}
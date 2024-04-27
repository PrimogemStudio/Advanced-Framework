package com.primogemstudio.advancedfmk.simulator.roundtrip

import com.primogemstudio.advancedfmk.simulator.ContextWrapper
import com.primogemstudio.advancedfmk.simulator.ResultWrapper
import com.primogemstudio.advancedfmk.simulator.Simulator
import java.util.*
import kotlin.random.Random

class SimulatedUniverse(
    val characters: MutableList<CharacterBase> = mutableListOf(),
    val enemies: MutableList<CharacterBase> = mutableListOf(),
    val funcRequestTarget: (TargetRequestContextWrapper) -> IntArray,
    val funcUncontrolledRequestTarget: (TargetRequestContextWrapper) -> IntArray
): Simulator({
    ResultWrapper(
        characters.map { it.calcHealth() }.sum() == 0f ||
                enemies.map { it.calcHealth() }.sum() == 0f
    )
}) {
    private val operateQueue: Deque<MutableList<CharacterBase>> = LinkedList()
    override fun simulateStep(context: ContextWrapper) {
        if (operateQueue.isEmpty()) {
            characters.forEach { operateQueue.offer(mutableListOf(it)) }
            enemies.forEach { operateQueue.offer(mutableListOf(it)) }
        }

        val cu = operateQueue.poll()
        while (cu.size > 0) {
            val current = cu[0]
            when (current.type()) {
                CharacterBase.Type.Controllable -> enemies
                CharacterBase.Type.UnControllable -> characters
            }.apply {
                (if (this == characters) funcUncontrolledRequestTarget else funcRequestTarget)(
                    TargetRequestContextWrapper(
                        this@SimulatedUniverse, current, this
                    )
                ).map { this[it] }.forEach {
                    it.operateHealth { it.setter.call(it.getter.call() - current.calcOutputMain() * Random.nextInt(95, 105).toFloat() / 100f) }
                }
            }
            cu.removeAt(0)
        }
    }
}
package com.primogemstudio.advancedfmk.simulator.roundtrip

import com.primogemstudio.advancedfmk.simulator.ContextWrapper
import com.primogemstudio.advancedfmk.simulator.ResultWrapper
import com.primogemstudio.advancedfmk.simulator.Simulator
import java.util.*
import kotlin.random.Random

class SimulatedUniverse(
    val characters: MutableList<CharacterBase> = mutableListOf(),
    val enemies: MutableList<CharacterBase> = mutableListOf()
): Simulator({
    ResultWrapper(
        characters.map { it.calcHealth() }.sum() == 0f ||
                enemies.map { it.calcHealth() }.sum() == 0f
    )
}) {
    private val operateQueue: Queue<CharacterBase> = LinkedList()
    override fun simulateStep(context: ContextWrapper) {
        if (operateQueue.isEmpty()) {
            characters.forEach { operateQueue.add(it) }
            enemies.forEach { operateQueue.add(it) }
        }

        val current = operateQueue.poll()
        when (current.type()) {
            CharacterBase.Type.Controllable -> enemies
            CharacterBase.Type.UnControllable -> characters
        }.apply {
            var i = -1
            while (i == -1 || !this[i].alive()) {
                i = Random.nextInt(0, size)
            }
            this[i].operateHealth { it.setter.call(it.getter.call() - current.calcOutputMain()) }
        }
    }
}
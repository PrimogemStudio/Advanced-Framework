package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.BasicRoundtripCharacter
import java.util.*

class SimulatedUniverse(
    val characters: List<BasicRoundtripCharacter>,
    val enemies: List<BasicRoundtripCharacter>
) {
    private val operQueue: Deque<MutableList<BasicRoundtripCharacter>> = LinkedList()
    init {
        characters.forEach { operQueue.offer(mutableListOf(it)); it.simulator = this }
        enemies.forEach { operQueue.offer(mutableListOf(it)); it.simulator = this }
    }

    fun finished(): Boolean = characters.map { it.health }.sum() == 0f || win()
    fun win(): Boolean = enemies.map { it.health }.sum() == 0f
    fun getQueueTop(): BasicRoundtripCharacter? = operQueue.peek().firstOrNull()
    fun getCurrTarget(c: BasicRoundtripCharacter): List<BasicRoundtripCharacter> {
        if (characters.contains(c)) return enemies
        if (enemies.contains(c)) return characters
        return listOf()
    }
}
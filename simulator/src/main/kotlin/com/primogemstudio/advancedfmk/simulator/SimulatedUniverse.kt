package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.IRoundtripCharacter
import java.util.*

enum class OperationState {
    NORMAL
}

class SimulatedUniverse(
    val characters: List<IRoundtripCharacter>,
    val enemies: List<IRoundtripCharacter>
) {
    private val operQueue: Deque<MutableList<Pair<IRoundtripCharacter, OperationState>>> = LinkedList()
    init {
        characters.forEach { operQueue.offer(mutableListOf(Pair(it, OperationState.NORMAL))); it.simulator = this }
        enemies.forEach { operQueue.offer(mutableListOf(Pair(it, OperationState.NORMAL))); it.simulator = this }
    }

    fun finished(): Boolean = characters.map { if (it.alive) 1f else 0f }.sum() == 0f || win()
    fun win(): Boolean = enemies.map { if (it.alive) 1f else 0f }.sum() == 0f
    fun getQueueTop(): IRoundtripCharacter? = if (finished()) null else operQueue.peek().firstOrNull()?.first
    fun getCurrTarget(c: IRoundtripCharacter): List<IRoundtripCharacter> {
        if (characters.contains(c)) return enemies
        if (enemies.contains(c)) return characters
        return listOf()
    }
    fun operateDone(c: IRoundtripCharacter) {
        if (c == getQueueTop()) {
            val tg = operQueue.peek().firstOrNull { it.first == c }
            operQueue.peek().remove(tg)
            if (tg?.second == OperationState.NORMAL) operQueue.offer(operQueue.poll().apply { this.add(tg) })

            operQueue.forEach { stk -> stk.removeAll { !it.first.alive } }
            operQueue.removeAll { it.isEmpty() }
        }
    }

    fun mkSnapshot(res: AttackResult?): SnapshotResult {
        return SnapshotResult(
            characters.map { it.getRawData().toMap() },
            enemies.map { it.getRawData().toMap() },
            operQueue.map { it.toMutableList() }.toList(),
            res
        )
    }

    fun resSnapshot(snap: SnapshotResult) {
        for (i in snap.charactersData.indices) characters[i].overrideData(snap.charactersData[i])
        for (i in snap.enemiesData.indices) enemies[i].overrideData(snap.enemiesData[i])

        operQueue.clear()
        snap.operQueue.forEach { operQueue.add(it.toMutableList()) }
    }
}
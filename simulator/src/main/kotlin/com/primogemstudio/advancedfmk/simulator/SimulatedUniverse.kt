package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.OperationFlags.INSERTED
import com.primogemstudio.advancedfmk.simulator.OperationFlags.TURN_LENGTH
import com.primogemstudio.advancedfmk.simulator.objects.IRoundtripCharacter
import java.util.*

object OperationFlags {
    const val INSERTED: Long = 0x4000000000000000
    const val TURN_LENGTH: Long = 10000
}

class SimulatedUniverse(
    val characters: List<IRoundtripCharacter>,
    val enemies: List<IRoundtripCharacter>,
    maxNum: Int, currentM: Int
) {
    val operQueue: Deque<Pair<IRoundtripCharacter, Long>> = LinkedList()
    private var extendedVal = mutableMapOf<String, Any>()

    init {
        extendedVal["maxN"] = maxNum
        extendedVal["maxNCalc"] = currentM
        extendedVal["passedTime"] = 0L
    }

    var maxAttNum: Int
        get() = extendedVal["maxNCalc"] as Int
        set(v) { extendedVal["maxNCalc"] = v }
    var passedTime: Long
        get() = extendedVal["passedTime"] as Long
        set(v) { extendedVal["passedTime"] = v }

    init {
        characters.forEach { operQueue.offer(Pair(it, TURN_LENGTH / it.speed)); it.simulator = this }
        enemies.forEach { operQueue.offer(Pair(it, TURN_LENGTH / it.speed)); it.simulator = this }
        refreshQueue(null)
    }

    private fun refreshQueue(top: IRoundtripCharacter?) {
        var res = operQueue.map { if (it.first == top) Pair(it.first, it.second + TURN_LENGTH / it.first.speed) else it }.sortedBy { it.second xor INSERTED }
        val opp = res.first().second
        passedTime += opp
        res = res.map { Pair(it.first, it.second - opp) }

        operQueue.removeIf { true }
        operQueue.addAll(res)
    }

    fun finished(): Boolean = characters.map { it.health }.sum() <= 0f || win()
    fun win(): Boolean = enemies.map { it.health }.sum() <= 0f
    fun getQueueTop(): IRoundtripCharacter? = operQueue.peek()?.first
    fun getCurrTarget(c: IRoundtripCharacter): List<IRoundtripCharacter> {
        if (characters.contains(c)) return enemies
        if (enemies.contains(c)) return characters
        return listOf()
    }
    fun getCurrList(c: IRoundtripCharacter): List<IRoundtripCharacter> {
        if (characters.contains(c)) return characters
        if (enemies.contains(c)) return enemies
        return listOf()
    }
    fun operateDone(c: IRoundtripCharacter) {
        if (c == getQueueTop()) {
            operQueue.removeIf { !it.first.alive }
            refreshQueue(c)
        }
    }

    fun mkSnapshot(res: AttackResult?): SnapshotResult {
        return SnapshotResult(
            characters.map { it.getRawData().toMap() },
            enemies.map { it.getRawData().toMap() },
            operQueue.toList(),
            extendedVal.toMap(),
            res
        )
    }

    fun resSnapshot(snap: SnapshotResult) {
        for (i in snap.charactersData.indices) characters[i].overrideData(snap.charactersData[i])
        for (i in snap.enemiesData.indices) enemies[i].overrideData(snap.enemiesData[i])
        extendedVal = snap.extendedVals.toMutableMap()

        operQueue.clear()
        snap.operQueue.forEach { operQueue.add(it) }
    }
}
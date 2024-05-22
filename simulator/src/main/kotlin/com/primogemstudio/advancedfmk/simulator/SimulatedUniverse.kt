package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.OperationFlags.INSERTED
import com.primogemstudio.advancedfmk.simulator.OperationFlags.TURN_LENGTH
import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacter
import java.util.*

object OperationFlags {
    const val INSERTED = 2147483648u
    const val TURN_LENGTH = 10000u
}

class SimulatedUniverse(
    private val characters: List<RoundtripCharacter>,
    private val enemies: List<RoundtripCharacter>,
    maxNum: Int, currentM: Int
) {
    private val operateQueue: Deque<Pair<RoundtripCharacter, UInt>> = LinkedList()
    private var extendedVal = mutableMapOf<String, Any>()

    init {
        extendedVal["maxN"] = maxNum
        extendedVal["maxNCalc"] = currentM
        extendedVal["passedTime"] = 0u
    }

    var maxAttNum: Int
        get() = extendedVal["maxNCalc"] as Int
        set(v) { extendedVal["maxNCalc"] = v }
    private var passedTime: UInt
        get() = extendedVal["passedTime"] as UInt
        set(v) { extendedVal["passedTime"] = v }

    init {
        characters.forEach { operateQueue.offer(Pair(it, TURN_LENGTH / it.speed)); it.simulator = this }
        enemies.forEach { operateQueue.offer(Pair(it, TURN_LENGTH / it.speed)); it.simulator = this }
        refreshQueue(null)
    }

    private fun refreshQueue(top: RoundtripCharacter?) {
        if (operateQueue.first.first == top && operateQueue.first.second and INSERTED != 0u) operateQueue.pop()
        var res = operateQueue.map { if (it.first == top) Pair(it.first, it.second + TURN_LENGTH / it.first.speed) else it }.sortedBy { it.second and INSERTED.inv() }
        res.first().second.apply {
            passedTime += this
            res = res.map { Pair(it.first, it.second - this) }
        }

        operateQueue.removeIf { true }
        operateQueue.addAll(res)
    }

    fun finished(): Boolean = characters.map { it.health }.sum() <= 0f || win()
    fun win(): Boolean = enemies.map { it.health }.sum() <= 0f
    fun getQueueTop(): RoundtripCharacter? = operateQueue.peek()?.first
    fun getCurrTarget(c: RoundtripCharacter): List<RoundtripCharacter> {
        if (characters.contains(c)) return enemies
        if (enemies.contains(c)) return characters
        return listOf()
    }
    fun getCurrList(c: RoundtripCharacter): List<RoundtripCharacter> {
        if (characters.contains(c)) return characters
        if (enemies.contains(c)) return enemies
        return listOf()
    }
    fun operateDone(c: RoundtripCharacter) {
        if (c == getQueueTop()) {
            operateQueue.removeIf { !it.first.alive }
            refreshQueue(c)
        }
    }

    fun mkSnapshot(res: AttackResult?): SnapshotResult {
        return SnapshotResult(
            characters.map { it.rawData },
            enemies.map { it.rawData },
            operateQueue.toList(),
            extendedVal.toMap(),
            res
        )
    }

    fun resSnapshot(snap: SnapshotResult) {
        for (i in snap.charactersData.indices) characters[i].rawData = snap.charactersData[i]
        for (i in snap.enemiesData.indices) enemies[i].rawData = snap.enemiesData[i]
        extendedVal = snap.extendedVals.toMutableMap()

        operateQueue.clear()
        snap.operQueue.forEach { operateQueue.add(it) }
    }
}
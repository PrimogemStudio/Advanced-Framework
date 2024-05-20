package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.IRoundtripCharacter
import java.util.*

object OperationFlags {
    const val INSERTED: Int = 0x40000000
}

class SimulatedUniverse(
    val characters: List<IRoundtripCharacter>,
    val enemies: List<IRoundtripCharacter>,
    maxNum: Int, currentM: Int
) {
    private val operQueue: Deque<Pair<IRoundtripCharacter, Int>> = LinkedList()
    private var extendedVal = mutableMapOf<String, Any>()

    init {
        extendedVal["maxN"] = maxNum
        extendedVal["maxNCalc"] = currentM
    }

    var maxAttNum: Int
        get() = extendedVal["maxNCalc"] as Int
        set(v) { extendedVal["maxNCalc"] = v }

    init {
        characters.forEach { operQueue.offer(Pair(it, 0)); it.simulator = this }
        enemies.forEach { operQueue.offer(Pair(it, 0)); it.simulator = this }
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
            val tg = operQueue.firstOrNull { it.first == c }
            operQueue.remove(tg)

            if (tg?.second!! and OperationFlags.INSERTED == 0) operQueue.offer(tg)

            operQueue.removeIf { !it.first.alive }
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
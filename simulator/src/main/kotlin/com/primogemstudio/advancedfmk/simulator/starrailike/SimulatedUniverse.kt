package com.primogemstudio.advancedfmk.simulator.starrailike

import com.primogemstudio.advancedfmk.simulator.starrailike.objects.constraints.OBJECT_GLB_PASSED_TIME
import com.primogemstudio.advancedfmk.simulator.starrailike.objects.constraints.OPSTK_FLAG_SPECIAL
import com.primogemstudio.advancedfmk.simulator.starrailike.objects.constraints.OPSTK_TURN_LENGTH
import com.primogemstudio.advancedfmk.simulator.starrailike.objects.interfaces.CharacterObject
import com.primogemstudio.advancedfmk.simulator.starrailike.objects.interfaces.EnemyObject
import com.primogemstudio.advancedfmk.simulator.starrailike.objects.interfaces.RoundtripObject
import java.util.*

class SimulatedUniverse<C, E>(
    private val characters: List<C>,
    private val enemies: List<E>,
    maxNum: Int, currentM: Int
) where C: RoundtripObject, C: CharacterObject, E: RoundtripObject, E: EnemyObject {
    private val operateQueue: Deque<Pair<RoundtripObject, UInt>> = LinkedList()
    private var extendedVal = mutableMapOf<String, Any>()

    init {
        extendedVal["maxN"] = maxNum
        extendedVal["maxNCalc"] = currentM
        extendedVal[OBJECT_GLB_PASSED_TIME] = 0u
        characters.forEach { operateQueue.offer(Pair(it, OPSTK_TURN_LENGTH / it.spd)); it.simulator = this }
        enemies.forEach { operateQueue.offer(Pair(it, OPSTK_TURN_LENGTH / it.spd)); it.simulator = this }
        refreshQueue(null)
    }

    var maxAttNum: Int
        get() = extendedVal["maxNCalc"] as Int
        set(v) { extendedVal["maxNCalc"] = v }
    private var passedTime: UInt
        get() = extendedVal[OBJECT_GLB_PASSED_TIME] as UInt
        set(v) { extendedVal[OBJECT_GLB_PASSED_TIME] = v }

    private fun refreshQueue(top: RoundtripObject?) {
        if (operateQueue.first.first == top && operateQueue.first.second and OPSTK_FLAG_SPECIAL != 0u) operateQueue.pop()
        var res = operateQueue.map { if (it.first == top) Pair(it.first, it.second + OPSTK_TURN_LENGTH / it.first.spd) else it }.sortedBy { it.second and OPSTK_FLAG_SPECIAL.inv() }
        res.first().second.apply {
            passedTime += this
            res = res.map { Pair(it.first, it.second - this) }
        }

        operateQueue.removeIf { true }
        operateQueue.addAll(res)
    }

    fun finished(): Boolean = characters.map { it.health }.sum() <= 0f || win()
    fun win(): Boolean = enemies.map { it.health }.sum() <= 0f
    fun getQueueTop(): RoundtripObject? = operateQueue.peek()?.first
    fun getCurrTarget(c: RoundtripObject): List<RoundtripObject> {
        if (characters.contains(c)) return enemies
        if (enemies.contains(c)) return characters
        return listOf()
    }
    fun getCurrList(c: RoundtripObject): List<RoundtripObject> {
        if (characters.contains(c)) return characters
        if (enemies.contains(c)) return enemies
        return listOf()
    }
    fun operateDone(c: RoundtripObject) {
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
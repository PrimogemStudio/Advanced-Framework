package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.AttackResult
import com.primogemstudio.advancedfmk.simulator.SimulatedUniverse
import com.primogemstudio.advancedfmk.simulator.objects.constraints.*
import com.primogemstudio.advancedfmk.simulator.objects.interfaces.RoundtripObject
import kotlin.math.max

open class RoundtripCharacterImplV0(
    id: String,
    initHp: Float,
    dmg: Float,
    spd: UInt
): RoundtripObject {
    protected var initialData = mutableMapOf<String, Any>()
    final override val staticData: Map<String, Any> = mutableMapOf()

    init {
        initialData[OBJECT_ID] = id
        initialData[OBJECT_HP] = initHp
        initialData[OBJECT_SPD] = spd
        (staticData as MutableMap).apply {
            this[OBJECT_ST_DMG] = dmg
            this[OBJECT_ST_ALLHP] = initHp
        }
    }

    override val id: String
        get() = initialData[OBJECT_ID] as String
    override var health: Float
        get() = initialData[OBJECT_HP] as Float
        set(value) { initialData[OBJECT_HP] = value }

    override val allHealth: Float
        get() = staticData[OBJECT_ST_ALLHP] as Float

    override val dmg: Float
        get() = staticData[OBJECT_ST_DMG] as Float
    override val alive: Boolean
        get() = health > 0f

    override var spd: UInt
        get() = initialData[OBJECT_SPD] as UInt
        set(value) { initialData[OBJECT_SPD] = value }

    override fun receiveAttack(value: Float, additional: Map<String, Any>) {
        health = max(0f, health - value)
    }

    override var simulator: SimulatedUniverse? = null
    override var rawData: Map<String, Any>
        get() = initialData.toMap()
        set(v) { initialData = v.toMutableMap() }
    override fun getSolutions(): List<() -> AttackResult> {
        if (simulator?.getQueueTop() != this) return listOf()
        return simulator?.getCurrTarget(this)?.filter { it.alive }?.let { l ->
            l.flatMap {
                listOf {
                    it.receiveAttack(dmg, mapOf())
                    AttackResult(this, mapOf(Pair(it, dmg)), 1.0 / l.size)
                }
            }
        }?: listOf()
    }

    override fun finishSolve(): Unit = simulator?.operateDone(this)!!
    override fun toString(): String = id
}
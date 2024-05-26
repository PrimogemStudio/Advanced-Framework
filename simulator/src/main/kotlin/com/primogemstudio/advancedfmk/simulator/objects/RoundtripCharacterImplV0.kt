package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.AttackResult
import com.primogemstudio.advancedfmk.simulator.SimulatedUniverse
import com.primogemstudio.advancedfmk.simulator.objects.interfaces.RoundtripObject
import kotlin.math.max

open class RoundtripCharacterImplV0(
    id: String,
    initHp: Float,
    dmg: Float,
    spd: UInt
): RoundtripObject {
    private var initialData = mutableMapOf<String, Any>()
    final override val staticData: Map<String, Any> = mutableMapOf()

    init {
        initialData["id"] = id
        initialData["hp"] = initHp
        initialData["spd"] = spd
        (staticData as MutableMap)["dmg"] = dmg
        staticData["allHp"] = initHp
    }

    override val id: String
        get() = initialData["id"] as String
    override var health: Float
        get() = initialData["hp"] as Float
        set(value) { initialData["hp"] = value }

    override val allHealth: Float
        get() = staticData["allHp"] as Float

    override val dmg: Float
        get() = staticData["dmg"] as Float
    override val alive: Boolean
        get() = health > 0f

    override var spd: UInt
        get() = initialData["spd"] as UInt
        set(value) { initialData["spd"] = value }

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
                    val r = dmg
                    it.receiveAttack(r, mapOf())
                    AttackResult(this, mapOf(Pair(it, r)), 1.0 / l.size)
                }
            }
        }?: listOf()
    }

    override fun finishSolve(): Unit = simulator?.operateDone(this)!!
    override fun toString(): String = id
}
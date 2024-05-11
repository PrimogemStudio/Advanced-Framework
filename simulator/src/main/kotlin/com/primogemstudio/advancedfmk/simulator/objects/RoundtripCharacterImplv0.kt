package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.AttackResult
import com.primogemstudio.advancedfmk.simulator.SimulatedUniverse
import kotlin.math.max

class RoundtripCharacterImplv0(
    id: String,
    initHealth: Float,
    output: Float
): IRoundtripCharacter {
    private var initialData = mutableMapOf<String, Any>()
    init {
        initialData["id"] = id
        initialData["allHealth"] = initHealth
        initialData["health"] = initHealth
        initialData["output"] = output
    }

    override val id: String
        get() = initialData["id"] as String
    override var health: Float
        get() = initialData["health"] as Float
        set(value) { initialData["health"] = value }

    override val allHealth: Float
        get() = initialData["allHealth"] as Float

    override val mainOutput: Float
        get() = initialData["output"] as Float
    override val alive: Boolean
        get() = health > 0f

    override fun receiveAttack(value: Float, additional: Map<String, Any>) {
        health = max(0f, health - value)
    }

    override var simulator: SimulatedUniverse? = null

    override fun getRawData(): Map<String, Any> = initialData
    override fun overrideData(v: Map<String, Any>) { initialData = v.toMutableMap() }
    override fun getSolutions(): List<() -> AttackResult> {
        if (simulator?.getQueueTop() != this) return listOf()
        return simulator?.getCurrTarget(this)?.filter { it.alive }?.flatMap {
            listOf(
                {
                    val r = mainOutput * 10005f / 10000f
                    it.receiveAttack(r, mapOf())
                    AttackResult(this, mapOf(Pair(it, r)))
                },
                {
                    val r = mainOutput * 9995f / 10000f
                    it.receiveAttack(r, mapOf())
                    AttackResult(this, mapOf(Pair(it, r)))
                }
            )
        }?: listOf()
    }

    override fun finishSolve(): Unit = simulator?.operateDone(this)!!
    override fun toString(): String = id
}
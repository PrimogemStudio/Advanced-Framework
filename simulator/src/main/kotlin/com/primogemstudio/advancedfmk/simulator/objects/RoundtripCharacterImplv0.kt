package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.SimulatedUniverse
import kotlin.math.max

class RoundtripCharacterImplv0(
    id: String,
    initHealth: Float,
    output: Float
): BasicRoundtripCharacter {
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

    override fun receiveAttack(value: Float) {
        health = max(0f, health - value)
    }

    override var simulator: SimulatedUniverse? = null

    override fun getRawData(): Map<String, Any> = initialData
    override fun overrideData(v: Map<String, Any>) { initialData = v.toMutableMap() }
    override fun getSolutions(): List<() -> Unit> {
        if (simulator?.getQueueTop() != this) return listOf()
        return simulator?.getCurrTarget(this)?.map { { it.receiveAttack(mainOutput) } }?: listOf()
    }
}
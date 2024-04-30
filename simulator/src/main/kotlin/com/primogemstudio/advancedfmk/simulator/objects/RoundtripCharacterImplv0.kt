package com.primogemstudio.advancedfmk.simulator.objects

class RoundtripCharacterImplv0(
    initHealth: Float,
    output: Float
): BasicRoundtripCharacter {
    private var initialData = mutableMapOf<String, Any>()
    init {
        initialData["allHealth"] = initHealth
        initialData["health"] = initHealth
        initialData["output"] = output
    }

    override var health: Float
        get() = initialData["health"] as Float
        set(value) { initialData["health"] = value }

    override val allHealth: Float
        get() = initialData["allHealth"] as Float

    override val mainOutput: Float
        get() = initialData["output"] as Float

    override fun getRawData(): Map<String, Any> = initialData
    override fun overrideData(v: Map<String, Any>) { initialData = v.toMutableMap() }
}
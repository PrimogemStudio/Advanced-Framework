package com.primogemstudio.advancedfmk.simulator.roundtrip

import kotlin.math.max
import kotlin.reflect.KMutableProperty

data class DefaultedObject(
    val id: String,
    val health: Float,
    val mainOutput: Float,
    val type: CharacterBase.Type
): CharacterBase {
    var currentHealth = health
        set(value) { field = max(0f, value) }
    override fun type(): CharacterBase.Type = type

    override fun calcHealth(): Float = currentHealth
    override fun calcOutputMain(): Float = mainOutput

    override fun alive(): Boolean = currentHealth > 0
    override fun reset() {
        currentHealth = health
    }

    override fun operateHealth(func: (KMutableProperty<Float>) -> Unit) = func(this::currentHealth)
    override fun toString(): String = id
    override fun getName(): String = id
}

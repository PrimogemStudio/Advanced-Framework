package com.primogemstudio.advancedfmk.simulator.roundtrip

import kotlin.math.max
import kotlin.random.Random
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
    override fun calcOutputMain(): Float = mainOutput * Random.nextInt(9500, 10500).toFloat() / 10000f

    override fun alive(): Boolean = currentHealth > 0
    override fun reset() {
        currentHealth = health
    }

    override fun operateHealth(func: (KMutableProperty<Float>) -> Unit) = func(this::currentHealth)
    override fun toString(): String = id
    override fun getName(): String = id

    override fun clone(): CharacterBase = DefaultedObject(id, health, mainOutput, type).apply { this.currentHealth = currentHealth }
    override fun selectTargets(context: TargetRequestContextWrapper): IntArray {
        var i = -1
        while (i == -1 || !context.targetObjects[i].alive()) {
            i = Random.nextInt(0, context.targetObjects.size)
        }
        return intArrayOf(i)
    }
}

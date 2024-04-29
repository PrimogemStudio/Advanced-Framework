package com.primogemstudio.advancedfmk.simulator.roundtrip

import com.primogemstudio.advancedfmk.simulator.Simulator
import kotlin.math.max
import kotlin.random.Random

data class DefaultedObject(
    val id: String,
    val health: Float,
    val mainOutput: Float,
    val type: CharacterBase.Type,
    override var simulator: Simulator
): CharacterBase {
    private var currentHealth = health
        set(value) { field = max(0f, value) }
    override fun type(): CharacterBase.Type = type

    override fun calcHealth(): Float = currentHealth
    override fun calcOutputMain(): Float = mainOutput * Random.nextInt(9500, 10500).toFloat() / 10000f

    override fun alive(): Boolean = currentHealth > 0
    override fun reset() {
        currentHealth = health
    }

    override fun modHealth(add: Float) { currentHealth += add }
    override fun toString(): String = id
    override fun getName(): String = id

    override fun clone(): CharacterBase = DefaultedObject(id, health, mainOutput, type, simulator).apply { this.currentHealth = currentHealth }

    override fun simulateStep(context: TargetRequestContextWrapper): OperationDataWrapper {
        var i = -1
        while (i == -1 || !context.targetObjects[i].alive()) {
            i = Random.nextInt(0, context.targetObjects.size)
        }
        val data = OperationDataWrapper(this)
        val t = calcOutputMain() * Random.nextInt(9995, 10005).toFloat() / 10000f

        context.targetObjects[i].modHealth(-t)
        data.targets[context.targetObjects[i]] = t
        return data
    }
}

package com.primogemstudio.advancedfmk.simulator.roundtrip

import com.primogemstudio.advancedfmk.simulator.Simulator

interface CharacterBase: Cloneable {
    fun type(): Type

    fun calcHealth(): Float
    fun calcOutputMain(): Float

    fun reset()
    fun alive(): Boolean
    fun modHealth(add: Float)
    fun getName(): String
    fun simulateStep(context: TargetRequestContextWrapper): OperationDataWrapper
    var simulator: Simulator

    public override fun clone(): CharacterBase

    enum class Type {
        Controllable,
        UnControllable,
        UnControllableUnrequired
    }
}
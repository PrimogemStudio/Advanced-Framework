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
    fun getAllChoices(): List<(TargetRequestContextWrapper) -> OperationDataWrapper>
    fun getChoicesCount(): Int
    var simulator: Simulator
    var currentChoice: Int

    public override fun clone(): CharacterBase

    enum class Type {
        Controllable,
        UnControllable,
        UnControllableUnrequired
    }
}
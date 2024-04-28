package com.primogemstudio.advancedfmk.simulator.roundtrip

import kotlin.reflect.KMutableProperty

interface CharacterBase: Cloneable {
    fun type(): Type

    fun calcHealth(): Float
    fun calcOutputMain(): Float

    fun reset()
    fun alive(): Boolean
    fun operateHealth(func: (KMutableProperty<Float>) -> Unit)
    fun getName(): String
    fun selectTargets(context: TargetRequestContextWrapper): IntArray

    public override fun clone(): CharacterBase

    enum class Type {
        Controllable,
        UnControllable,
        UnControllableUnrequired
    }
}
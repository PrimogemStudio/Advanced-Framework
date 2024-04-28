package com.primogemstudio.advancedfmk.simulator.roundtrip

import com.primogemstudio.advancedfmk.simulator.Simulator
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
    var simulator: Simulator

    public override fun clone(): CharacterBase

    enum class Type {
        Controllable,
        UnControllable,
        UnControllableUnrequired
    }
}
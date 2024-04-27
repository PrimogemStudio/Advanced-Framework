package com.primogemstudio.advancedfmk.simulator.roundtrip

import kotlin.reflect.KMutableProperty

interface CharacterBase {
    fun type(): Type

    fun calcHealth(): Float
    fun calcOutputMain(): Float

    fun reset()
    fun alive(): Boolean
    fun operateHealth(func: (KMutableProperty<Float>) -> Unit)

    enum class Type {
        Controllable,
        UnControllable
    }
}
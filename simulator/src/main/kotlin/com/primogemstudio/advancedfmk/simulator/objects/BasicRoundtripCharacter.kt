package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.SimulatedUniverse

interface BasicRoundtripCharacter: MappedObject {
    val id: String

    var health: Float
    val allHealth: Float

    val mainOutput: Float
    var simulator: SimulatedUniverse?

    fun receiveAttack(value: Float)
    fun getSolutions(): List<() -> Unit>
    fun finishSolve()
}
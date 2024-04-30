package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.AttackResult
import com.primogemstudio.advancedfmk.simulator.SimulatedUniverse

interface BasicRoundtripCharacter: MappedObject {
    val id: String

    var health: Float
    val allHealth: Float

    val mainOutput: Float
    var simulator: SimulatedUniverse?
    val alive: Boolean

    fun receiveAttack(value: Float, additional: Map<String, Any>)
    fun getSolutions(): List<() -> AttackResult>
    fun finishSolve()
}
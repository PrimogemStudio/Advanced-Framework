package com.primogemstudio.advancedfmk.simulator.starrailike.objects.interfaces

import com.primogemstudio.advancedfmk.simulator.starrailike.AttackResult
import com.primogemstudio.advancedfmk.simulator.starrailike.SimulatedUniverse

interface RoundtripObject: MappedObject {
    val id: String

    var health: Float
    val allHealth: Float

    val dmg: Float
    var simulator: SimulatedUniverse?
    val alive: Boolean

    var spd: UInt

    fun receiveAttack(value: Float, additional: Map<String, Any>)
    fun getSolutions(): List<() -> AttackResult>
    fun finishSolve()
    override fun toString(): String
}
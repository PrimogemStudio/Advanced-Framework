package com.primogemstudio.advancedfmk.simulator.objects

interface BasicRoundtripCharacter: MappedObject {
    var health: Float
    val allHealth: Float

    val mainOutput: Float
}
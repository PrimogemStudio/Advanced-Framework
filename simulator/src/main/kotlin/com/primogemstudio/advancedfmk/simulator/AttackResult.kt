package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.IRoundtripCharacter

data class AttackResult(
    val from: IRoundtripCharacter?,
    val targets: Map<IRoundtripCharacter, Float>
)

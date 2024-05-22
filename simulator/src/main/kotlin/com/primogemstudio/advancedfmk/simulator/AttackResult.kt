package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacter

data class AttackResult(
    val from: RoundtripCharacter?,
    val targets: Map<RoundtripCharacter, Float>
)

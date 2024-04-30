package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.BasicRoundtripCharacter

data class AttackResult(
    val from: BasicRoundtripCharacter?,
    val targets: Map<BasicRoundtripCharacter, Float>
)

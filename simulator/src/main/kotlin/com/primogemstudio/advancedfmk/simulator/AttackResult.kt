package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.RoundtripObject

data class AttackResult(
    val from: RoundtripObject?,
    val targets: Map<RoundtripObject, Float>
)

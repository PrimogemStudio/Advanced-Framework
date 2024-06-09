package com.primogemstudio.advancedfmk.simulator.starrailike

import com.primogemstudio.advancedfmk.simulator.starrailike.objects.interfaces.RoundtripObject

data class AttackResult(
    val from: RoundtripObject?,
    val targets: Map<RoundtripObject, Float>,
    val weight: Double
)

package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.objects.constraints.ObjectWeakness

class EnemyObjectImpl(
    id: String,
    initHealth: Float,
    output: Float,
    speed: UInt,
    override val weakness: List<ObjectWeakness>
): RoundtripCharacterImplV0(id, initHealth, output, speed), EnemyObject
package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.objects.constraints.ObjectWeakness
import com.primogemstudio.advancedfmk.simulator.objects.interfaces.EnemyObject

class EnemyObjectImpl(
    id: String,
    initHp: Float,
    dmg: Float,
    spd: UInt,
    override val weakness: MutableList<ObjectWeakness>
): RoundtripCharacterImplV0(id, initHp, dmg, spd), EnemyObject
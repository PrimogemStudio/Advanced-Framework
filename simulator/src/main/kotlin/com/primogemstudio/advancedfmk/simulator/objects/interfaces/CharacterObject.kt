package com.primogemstudio.advancedfmk.simulator.objects.interfaces

import com.primogemstudio.advancedfmk.simulator.objects.constraints.ObjectWeakness

interface CharacterObject {
    var critRate: Float
    var critDmg: Float

    val initCritRate: Float
    val initCritDmg: Float
    val dmgElement: ObjectWeakness
}
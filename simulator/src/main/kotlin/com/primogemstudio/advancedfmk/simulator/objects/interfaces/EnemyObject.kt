package com.primogemstudio.advancedfmk.simulator.objects.interfaces

import com.primogemstudio.advancedfmk.simulator.objects.constraints.ObjectWeakness

interface EnemyObject {
    val weakness: List<ObjectWeakness>
}
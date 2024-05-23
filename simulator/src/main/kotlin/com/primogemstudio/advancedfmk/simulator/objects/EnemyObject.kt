package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.objects.constraints.ObjectWeakness

interface EnemyObject {
    val weakness: List<ObjectWeakness>
}
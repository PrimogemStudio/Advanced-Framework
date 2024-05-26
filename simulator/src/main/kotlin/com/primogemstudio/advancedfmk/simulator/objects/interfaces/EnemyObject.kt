package com.primogemstudio.advancedfmk.simulator.objects.interfaces

import com.primogemstudio.advancedfmk.simulator.objects.constraints.ObjectWeakness

interface EnemyObject {
    fun appendWeakness(chr: RoundtripObject?, prop: ObjectWeakness): Boolean
    fun removeWeakness(chr: RoundtripObject?, prop: ObjectWeakness): Boolean

    fun haveWeakness(prop: ObjectWeakness): Boolean

    val initToughness: Int
    var toughness: Int
    val toughnessBreaked: Boolean
}
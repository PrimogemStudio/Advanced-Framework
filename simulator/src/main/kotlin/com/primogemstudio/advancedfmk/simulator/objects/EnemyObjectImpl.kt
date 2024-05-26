package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.objects.constraints.OBJECT_EEXT_WEAKNESS
import com.primogemstudio.advancedfmk.simulator.objects.constraints.ObjectWeakness
import com.primogemstudio.advancedfmk.simulator.objects.interfaces.EnemyObject
import com.primogemstudio.advancedfmk.simulator.objects.interfaces.RoundtripObject

class EnemyObjectImpl(
    id: String,
    initHp: Float,
    dmg: Float,
    spd: UInt,
    initWeakness: List<ObjectWeakness>
): RoundtripObjectImplV0(id, initHp, dmg, spd), EnemyObject {
    init {
        initialData[OBJECT_EEXT_WEAKNESS] = initWeakness.toMutableList()
    }

    @Suppress("UNCHECKED_CAST")
    private val weakness
        get() = initialData[OBJECT_EEXT_WEAKNESS] as MutableList<ObjectWeakness>

    override fun appendWeakness(chr: RoundtripObject?, prop: ObjectWeakness): Boolean = weakness.add(prop)
    override fun removeWeakness(chr: RoundtripObject?, prop: ObjectWeakness): Boolean = weakness.remove(prop)

    override fun haveWeakness(prop: ObjectWeakness): Boolean = weakness.contains(prop)
}
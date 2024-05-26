package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.objects.constraints.*
import com.primogemstudio.advancedfmk.simulator.objects.interfaces.EnemyObject
import com.primogemstudio.advancedfmk.simulator.objects.interfaces.RoundtripObject
import kotlin.math.max

class EnemyObjectImpl(
    id: String,
    initHp: Float,
    dmg: Float,
    spd: UInt,
    initWeakness: List<ObjectWeakness>,
    toughness: Int
): RoundtripObjectImplV0(id, initHp, dmg, spd), EnemyObject {
    init {
        initialData[OBJECT_EEXT_WEAKNESS] = initWeakness.toMutableList()
        initialData[OBJECT_EEXT_TOUGHNESS] = toughness
        modStaticData(OBJECT_EEXT_ST_TOUGHNESS, toughness)
    }

    @Suppress("UNCHECKED_CAST")
    private val weakness
        get() = initialData[OBJECT_EEXT_WEAKNESS] as MutableList<ObjectWeakness>

    override fun receiveAttack(value: Float, additional: Map<String, Any>) {
        super.receiveAttack(value, additional)
        if (haveWeakness(additional[OBJECT_DYN_DMG_ELEMENT] as ObjectWeakness)) toughness -= (additional[OBJECT_DYN_ROUGHNESS_BREAK] as Number).toInt()
    }

    override fun appendWeakness(chr: RoundtripObject?, prop: ObjectWeakness): Boolean = weakness.add(prop)
    override fun removeWeakness(chr: RoundtripObject?, prop: ObjectWeakness): Boolean = weakness.remove(prop)

    override fun haveWeakness(prop: ObjectWeakness): Boolean = weakness.contains(prop)

    override val toughnessBreaked: Boolean
        get() = toughness == 0

    override var toughness: Int
        get() = initialData[OBJECT_EEXT_TOUGHNESS] as Int
        set(v) { initialData[OBJECT_EEXT_TOUGHNESS] = max(0, v) }

    override val initToughness: Int
        get() = staticData[OBJECT_EEXT_ST_TOUGHNESS] as Int
}
package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.AttackResult
import com.primogemstudio.advancedfmk.simulator.objects.constraints.OBJECT_CEXT_CRITDMG
import com.primogemstudio.advancedfmk.simulator.objects.constraints.OBJECT_CEXT_CRITRATE
import com.primogemstudio.advancedfmk.simulator.objects.constraints.OBJECT_CEXT_ST_CRITDMG
import com.primogemstudio.advancedfmk.simulator.objects.constraints.OBJECT_CEXT_ST_CRITRATE
import com.primogemstudio.advancedfmk.simulator.objects.interfaces.CharacterObject

class CharacterObjectImpl(
    id: String,
    initHp: Float,
    dmg: Float,
    spd: UInt,
    critRate: Float,
    critDmg: Float,
): RoundtripObjectImplV0(id, initHp, dmg, spd), CharacterObject {
    init {
        initialData[OBJECT_CEXT_CRITRATE] = critRate
        initialData[OBJECT_CEXT_CRITDMG] = critDmg

        modStaticData(OBJECT_CEXT_ST_CRITRATE, critRate)
        modStaticData(OBJECT_CEXT_ST_CRITDMG, critDmg)
    }

    override var critRate: Float
        get() = initialData[OBJECT_CEXT_CRITRATE] as Float
        set(v) { initialData[OBJECT_CEXT_CRITRATE] = v }

    override var critDmg: Float
        get() = initialData[OBJECT_CEXT_CRITDMG] as Float
        set(v) { initialData[OBJECT_CEXT_CRITDMG] = v }

    override val initCritRate: Float
        get() = staticData[OBJECT_CEXT_ST_CRITRATE] as Float

    override val initCritDmg: Float
        get() = staticData[OBJECT_CEXT_ST_CRITDMG] as Float

    override fun getSolutions(): List<() -> AttackResult> {
        if (simulator?.getQueueTop() != this) return listOf()
        return simulator?.getCurrTarget(this)?.filter { it.alive }?.let { l ->
            l.flatMap {
                listOf(
                    {
                        val r = dmg * (1 + critDmg)
                        it.receiveAttack(r, mapOf())
                        AttackResult(this, mapOf(Pair(it, r)), 1.0 / l.size * critRate)
                    },
                    {
                        val r = dmg
                        it.receiveAttack(r, mapOf())
                        AttackResult(this, mapOf(Pair(it, r)), 1.0 / l.size * (1 - critRate))
                    }
                )
            }
        }?: listOf()
    }
}
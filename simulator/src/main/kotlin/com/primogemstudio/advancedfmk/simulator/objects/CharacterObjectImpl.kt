package com.primogemstudio.advancedfmk.simulator.objects

import com.primogemstudio.advancedfmk.simulator.AttackResult

class CharacterObjectImpl(
    id: String,
    initHp: Float,
    dmg: Float,
    spd: UInt,
    private val critRate: Float,
    private val critDmg: Float,
): RoundtripCharacterImplV0(id, initHp, dmg, spd) {
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
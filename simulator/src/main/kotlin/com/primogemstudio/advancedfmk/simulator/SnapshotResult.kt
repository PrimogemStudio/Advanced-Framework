package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.BasicRoundtripCharacter

data class SnapshotResult(
    val charactersData: List<Map<String, Any>>,
    val enemiesData: List<Map<String, Any>>,
    val operQueue: List<MutableList<BasicRoundtripCharacter>>,
    val lastOperation: AttackResult?
) {
    fun finish(): Boolean = charactersData.map { it["health"] as Float }.sum() == 0f || win()
    fun win(): Boolean = enemiesData.map { it["health"] as Float }.sum() == 0f
}
package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.interfaces.RoundtripObject

data class SnapshotResult(
    val charactersData: List<Map<String, Any>>,
    val enemiesData: List<Map<String, Any>>,
    val operQueue: List<Pair<RoundtripObject, UInt>>,
    val extendedVals: Map<String, Any>,
    val lastOperation: AttackResult?
) {
    fun finish(): Boolean = charactersData.map { it["hp"] as Float }.sum() == 0f || win()
    fun win(): Boolean = enemiesData.map { it["hp"] as Float }.sum() == 0f

    override fun toString(): String = "${charactersData.map { it["hp"] }} ${enemiesData.map { it["hp"] }}"
}
package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacterImplv0

fun main() {
    val uni = SimulatedUniverse(
        listOf(
            RoundtripCharacterImplv0("Test character 1", 100f, 25f),
            RoundtripCharacterImplv0("Test character 2", 200f, 15f),
            RoundtripCharacterImplv0("Test character 3", 50f, 50f)
        ),
        listOf(
            RoundtripCharacterImplv0("Test enemy 1", 50f, 20f),
            RoundtripCharacterImplv0("Test enemy 2", 75f, 20f)
        )
    )
    println(uni.getQueueTop()?.getSolutions())
    println(uni.getQueueTop()?.finishSolve())
    println(uni.getQueueTop()?.getSolutions())
}
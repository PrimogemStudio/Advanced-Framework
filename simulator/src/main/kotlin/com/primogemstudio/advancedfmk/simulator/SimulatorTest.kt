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
    /*while (!uni.finished()) {
        val rs = uni.getQueueTop()?.getSolutions()?.get(0)?.invoke()
        uni.getQueueTop()?.finishSolve()
        val r = uni.mkSnapshot(rs!!)
        println(r)
    }
    println(uni)*/

    val r = uni.mkSnapshot(null)
    uni.getQueueTop()?.getSolutions()?.get(0)?.invoke()
    uni.getQueueTop()?.finishSolve()
    uni.resSnapshot(r)
    println(uni)
}
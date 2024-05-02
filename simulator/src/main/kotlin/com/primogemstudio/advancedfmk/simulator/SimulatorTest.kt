package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacterImplv0
import kotlin.math.max

class ResultTree: HashMap<SnapshotResult, ResultTree>()

var cont = 0
var depthd = 0
var all = 0f
var succ = 0f
fun genResult(uni: SimulatedUniverse, depth: Int = 0): ResultTree {
    cont++
    if (cont % 100000 == 0) System.gc()
    depthd = max(depthd, depth)

    val tar = ResultTree()
    if (uni.finished()) {
        all += 1
        if (uni.win()) succ += 1
        return tar
    }

    val root = uni.mkSnapshot(null)
    // println("Solving depth $depth, ${uni.getQueueTop()?.id}")
    for (i in uni.getQueueTop()?.getSolutions()!!) {
        val rs = i()
        uni.getQueueTop()?.finishSolve()
        tar[uni.mkSnapshot(rs)] = genResult(uni, depth + 1)
        uni.resSnapshot(root)
    }

    return tar
}

fun main() {
    val t = Thread.ofVirtual().start {
        while (true) {
            Thread.sleep(500)
            println("Current calcs: $cont $depthd ${succ / all * 100f} %")
        }
    }

    val uni = SimulatedUniverse(
        listOf(
            RoundtripCharacterImplv0("Test character 1", 100f, 25f * 0.7f),
            RoundtripCharacterImplv0("Test character 2", 200f, 15f * 0.7f),
            RoundtripCharacterImplv0("Test character 3", 50f, 50f * 0.7f)
        ),
        listOf(
            RoundtripCharacterImplv0("Test enemy 1", 50f * 2.3f, 20f),
            RoundtripCharacterImplv0("Test enemy 2", 75f * 2.3f, 20f)
        )
    )

    val r = genResult(uni)
    println(r.size)
    println(cont)
    println("${succ / all * 100f} %")
    t.interrupt()
}
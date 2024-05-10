package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacterImplv0

class ResultTree: HashMap<SnapshotResult, ResultTree>()

var cont = 0
var all = 0f
var succ = 0f
fun genResult(uni: SimulatedUniverse, depth: Int = 0): ResultTree {
    cont++
    if (cont % 1000000 == 0) System.gc()

    val tar = ResultTree()
    if (uni.finished()) {
        all += 1
        if (uni.win()) succ += 1
        return tar
    }

    val root = uni.mkSnapshot(null)
    for (i in uni.getQueueTop()?.getSolutions()!!) {
        val rs = i()
        uni.getQueueTop()?.finishSolve()
        tar[uni.mkSnapshot(rs)] = genResult(uni, depth + 1)
        uni.resSnapshot(root)
    }

    return tar
}

fun main() {
    val rr = { println("Current stat: $cont calcs, ${all.toInt()} ends, ${succ.toInt()} / ${all.toInt()}, ${succ / all * 100f} %") }
    val t = Thread.ofVirtual().start {
        while (true) {
            Thread.sleep(500)
            rr()
        }
    }

    val uni = SimulatedUniverse(
        listOf(
            RoundtripCharacterImplv0("Test character 1", 100f, 25f),
            RoundtripCharacterImplv0("Test character 2", 200f, 15f),
            RoundtripCharacterImplv0("Test character 3", 50f, 50f),
            RoundtripCharacterImplv0("Test character 3", 150f, 20f)
        ),
        listOf(
            RoundtripCharacterImplv0("Test enemy 1", 50f * 1.25f, 20f * 1.25f),
            RoundtripCharacterImplv0("Test enemy 2", 75f * 1.25f, 20f * 1.25f)
        ),
        5, 3
    )

    val r = genResult(uni)
    println(r.size)
    rr()
    t.interrupt()
}
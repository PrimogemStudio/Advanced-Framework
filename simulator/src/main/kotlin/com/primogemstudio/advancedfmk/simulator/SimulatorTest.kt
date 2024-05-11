package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacterImplv0
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream

var cont = 0
var all = 0f
var succ = 0f
val f = DeflaterOutputStream(Files.newOutputStream(Path.of("result.txt")), Deflater(Deflater.BEST_COMPRESSION)).bufferedWriter()
fun genResult(uni: SimulatedUniverse, depth: Int = 0) {
    cont++

    if (uni.finished()) {
        all += 1
        if (uni.win()) succ += 1
        return
    }

    val root = uni.mkSnapshot(null)
    for (i in uni.getQueueTop()?.getSolutions()!!) {
        val rs = i()
        uni.getQueueTop()?.finishSolve()
        f.appendLine("${"    ".repeat(depth)} $rs -> ")
        f.appendLine("${"    ".repeat(depth + 1)} ${root}")
        genResult(uni, depth + 1)
        uni.resSnapshot(root)
    }
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
            RoundtripCharacterImplv0("Test enemy 1", 50f * 3.5f, 20f * 3.5f),
            RoundtripCharacterImplv0("Test enemy 2", 75f * 3.5f, 20f * 3.5f)
        ),
        5, 3
    )

    genResult(uni)
    rr()
    t.interrupt()
}
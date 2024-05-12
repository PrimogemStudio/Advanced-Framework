package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacterImplv0
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream
import java.util.zip.InflaterInputStream

var cont = 0
var all: Double = 0.0
var succ: Double = 0.0
val f = DeflaterOutputStream(Files.newOutputStream(Path.of("result.txt")), Deflater(Deflater.BEST_COMPRESSION)).bufferedWriter()
fun genResult(uni: SimulatedUniverse, depth: Int = 0) {
    cont++

    if (uni.finished()) {
        all += 1
        if (uni.win()) succ += 1
        val re = uni.mkSnapshot(null)
        f.appendLine("${"    ".repeat(depth + 1)} ${re}")
        f.appendLine("${"    ".repeat(depth + 1)} finish win=${re.win()}")
        return
    }

    val root = uni.mkSnapshot(null)
    f.appendLine("${"    ".repeat(depth)} ${root}")
    for (i in uni.getQueueTop()?.getSolutions()!!) {
        val rs = i()
        uni.getQueueTop()?.finishSolve()
        f.appendLine("${"    ".repeat(depth + 1)} $rs -> ")
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
            RoundtripCharacterImplv0("Test enemy 1", 50f * 1.15f, 20f * 1.15f),
            RoundtripCharacterImplv0("Test enemy 2", 75f * 1.15f, 20f * 1.15f)
        ),
        5, 3
    )

    genResult(uni)
    rr()
    t.interrupt()
    f.close()
    val r = InflaterInputStream(Files.newInputStream(Path.of("result.txt"), StandardOpenOption.READ)).bufferedReader()
    while (true) {
        if (r.ready()) println(r.readLine())
        else break
    }
}
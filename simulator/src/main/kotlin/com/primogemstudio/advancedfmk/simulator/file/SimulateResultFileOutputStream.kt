package com.primogemstudio.advancedfmk.simulator.file

import com.primogemstudio.advancedfmk.simulator.SimulatedUniverse
import java.io.BufferedWriter
import java.io.OutputStream
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream
import java.util.zip.GZIPOutputStream

enum class Compressions(val func: (OutputStream) -> OutputStream) {
    GZIP({ GZIPOutputStream(it) }),
    DEFLATER({ DeflaterOutputStream(it, Deflater(Deflater.BEST_COMPRESSION)) });
}
class SimulateResultFileOutputStream(out: OutputStream): BufferedWriter(out.writer()) {
    constructor(out: OutputStream, c: Compressions): this(c.func(out))
    private var targetNum: Long = 0
    private var targetSucceed: Long = 0
    fun recStatus() = println("$targetSucceed/$targetNum, ${targetSucceed.toDouble() / targetNum.toDouble() * 100} %")
    fun simulate(uni: SimulatedUniverse, depth: Int = 0) {
        if (uni.finished()) {
            targetNum += 1
            if (uni.win()) targetSucceed += 1
            val re = uni.mkSnapshot(null)
            appendLine("${"    ".repeat(depth + 1)} ${re}")
            appendLine("${"    ".repeat(depth + 1)} finish win=${re.win()}")
            return
        }

        val root = uni.mkSnapshot(null)
        appendLine("${"    ".repeat(depth)} ${root}")
        for (i in uni.getQueueTop()?.getSolutions()!!) {
            val rs = i()
            i()
            uni.getQueueTop()?.finishSolve()
            appendLine("${"    ".repeat(depth + 1)} $rs -> ")
            simulate(uni, depth + 1)
            uni.resSnapshot(root)
        }
    }
}
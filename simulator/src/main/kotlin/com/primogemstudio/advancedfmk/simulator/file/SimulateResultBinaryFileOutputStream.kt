package com.primogemstudio.advancedfmk.simulator.file

import com.primogemstudio.advancedfmk.bin.NBTOutputStream
import com.primogemstudio.advancedfmk.simulator.SimulatedUniverse
import java.io.OutputStream

class SimulateResultBinaryFileOutputStream(out: OutputStream): NBTOutputStream(out) {
    constructor(out: OutputStream, c: Compressions): this(c.func(out))
    private var targetNum: Long = 0
    private var targetSucceed: Long = 0
    fun recStatus() = println("$targetSucceed/$targetNum, ${targetSucceed.toDouble() / targetNum.toDouble() * 100} %")
    fun simulate(uni: SimulatedUniverse, depth: Int = 0) {
        if (depth > 1) {
            writeByteTag("nonend", 0x00)
            return
        }
        if (uni.finished()) {
            targetNum += 1
            if (uni.win()) targetSucceed += 1
            val re = uni.mkSnapshot(null)
            writeListTag("characters", re.charactersData.map { it["health"] as Float })
            writeListTag("enemies", re.enemiesData.map { it["health"] as Float })
            writeByteTag("resultWin", if (re.win()) 0x01 else 0x00)
            return
        }

        val root = uni.mkSnapshot(null)
        writeListTag("characters", root.charactersData.map { it["health"] as Float })
        writeListTag("enemies", root.enemiesData.map { it["health"] as Float })
        for (i in uni.getQueueTop()?.getSolutions()!!) {
            val rs = i()
            i()
            uni.getQueueTop()?.finishSolve()

            writeCompoundTagHeader(rs.toString())
            simulate(uni, depth + 1)
            writeEndTag()

            uni.resSnapshot(root)
        }
    }
    fun writeRes(uni: SimulatedUniverse) {
        writeCompoundTagHeader("")
        simulate(uni)
        writeEndTag()
    }
}
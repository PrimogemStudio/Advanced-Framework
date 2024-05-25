package com.primogemstudio.advancedfmk.simulator.file

import com.primogemstudio.advancedfmk.bin.NBTOutputStream
import com.primogemstudio.advancedfmk.simulator.SimulatedUniverse
import org.apache.logging.log4j.LogManager
import java.io.OutputStream

enum class Compressions(val func: (OutputStream) -> OutputStream) {
    GZIP({ java.util.zip.GZIPOutputStream(it) }),
    DEFLATER({ java.util.zip.DeflaterOutputStream(it, java.util.zip.Deflater(java.util.zip.Deflater.BEST_COMPRESSION)) });
}

class SimulateResultBinaryFileOutputStream(out: OutputStream): NBTOutputStream(out) {
    @OptIn(ExperimentalStdlibApi::class)
    private val logger = LogManager.getLogger("SimulatedUniverse@0x${hashCode().toHexString()}")
    constructor(out: OutputStream, c: Compressions): this(c.func(out))
    private var targetNum: Long = 0
    private var targetSucceed: Long = 0
    fun recStatus() = logger.info("$targetSucceed/$targetNum, ${targetSucceed.toDouble() / targetNum.toDouble() * 100} %")
    private fun simulate(uni: SimulatedUniverse, depth: Int = 0, weight: Double = 1.0) {
        if (uni.finished()) {
            targetNum += 1
            if (uni.win()) targetSucceed += 1
            val re = uni.mkSnapshot(null)
            writeDoubleTag("weight", weight)
            writeListTag("characters", re.charactersData.map { it["health"] as Float })
            writeListTag("enemies", re.enemiesData.map { it["health"] as Float })
            writeByteTag("resultWin", if (re.win()) 0x01 else 0x00)
            return
        }

        val root = uni.mkSnapshot(null)
        writeDoubleTag("weight", weight)
        writeListTag("characters", root.charactersData.map { it["health"] as Float })
        writeListTag("enemies", root.enemiesData.map { it["health"] as Float })
        for (i in uni.getQueueTop()?.getSolutions()!!) {
            val rs = i()
            uni.getQueueTop()?.finishSolve()

            writeCompoundTagHeader(rs.toString())
            simulate(uni, depth + 1, weight * rs.weight)
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
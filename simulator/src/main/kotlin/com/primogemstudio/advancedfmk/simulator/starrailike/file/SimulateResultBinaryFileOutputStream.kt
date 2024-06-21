package com.primogemstudio.advancedfmk.simulator.starrailike.file

import com.primogemstudio.advancedfmk.bin.NBTOutputStream
import com.primogemstudio.advancedfmk.simulator.starrailike.SimulatedUniverse
import com.primogemstudio.advancedfmk.simulator.starrailike.objects.constraints.OBJECT_HP
import org.apache.logging.log4j.LogManager
import org.fusesource.jansi.Ansi
import org.jline.terminal.Terminal
import org.jline.terminal.TerminalBuilder
import java.io.OutputStream
import java.math.RoundingMode
import java.text.NumberFormat
import kotlin.math.max

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
    private var processed: Double = 0.0
    private var processed_suc: Double = 0.0
    private var processed_fai: Double = 0.0
    companion object {
        private var termWid = 0
        val terminal: Terminal = TerminalBuilder.builder()
            .system(true)
            .build()
            .apply { termWid = this.width }
        val nf = NumberFormat.getNumberInstance().apply {
            maximumFractionDigits = 8
            roundingMode = RoundingMode.HALF_UP
        }
    }
    fun recStatus() {
        print(Ansi.ansi().eraseLine(Ansi.Erase.ALL).saveCursorPosition())
        var pr = "  ${nf.format(processed * 100)} %"
        val textlength = max(pr.length, 20)
        val l = ((termWid - textlength).toDouble() * processed).toInt()
        val l2 = ((termWid - textlength).toDouble() * (1 - processed)).toInt()
        print(Ansi.ansi().fgBrightGreen().a("—".repeat(l)).fgBrightBlack().a("—".repeat(l2)).fgBrightYellow().a(pr).reset())
        print(Ansi.ansi().restoreCursorPosition())


        /*Pair(targetNum, (targetNum.toDouble() / processed).toLong()).apply {
            logger.info(
                "${Ansi.ansi().fgBrightBlue()}$first${Ansi.ansi().reset()}/${
                    Ansi.ansi().fgBrightYellow()
                }${if (first != second) "~" else ""}$second${Ansi.ansi().reset()}, ${
                    Ansi.ansi().fgBrightBlue()
                }${processed * 100} %${Ansi.ansi().reset()} -> ${
                    Ansi.ansi().fgBrightGreen()
                }${processed_suc * 100} % / ${Ansi.ansi().fgBrightRed()}${processed_fai * 100} % ${Ansi.ansi().reset()}"
            )
        }*/
    }
    private fun simulate(uni: SimulatedUniverse<*, *>, depth: Int = 0, weight: Double = 1.0) {
        if (uni.finished()) {
            targetNum += 1
            if (uni.win()) targetSucceed += 1
            val re = uni.mkSnapshot(null)
            processed += weight
            if (re.win()) processed_suc += weight else processed_fai += weight
            writeDoubleTag("weight", weight)
            writeListTag("characters", re.charactersData.map { it[OBJECT_HP] as Float })
            writeListTag("enemies", re.enemiesData.map { it[OBJECT_HP] as Float })
            writeByteTag("resultWin", if (re.win()) 0x01 else 0x00)
            return
        }

        val root = uni.mkSnapshot(null)
        writeDoubleTag("weight", weight)
        writeListTag("characters", root.charactersData.map { it[OBJECT_HP] as Float })
        writeListTag("enemies", root.enemiesData.map { it[OBJECT_HP] as Float })
        for (i in uni.getQueueTop()?.getSolutions()!!) {
            val rs = i()
            uni.getQueueTop()?.finishSolve()

            writeCompoundTagHeader(rs.toString())
            simulate(uni, depth + 1, weight * rs.weight)
            writeEndTag()

            uni.resSnapshot(root)
        }
    }
    fun writeRes(uni: SimulatedUniverse<*, *>) {
        writeCompoundTagHeader("")
        simulate(uni)
        writeEndTag()
    }
}
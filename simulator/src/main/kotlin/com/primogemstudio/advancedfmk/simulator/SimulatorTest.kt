package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.file.Compressions
import com.primogemstudio.advancedfmk.simulator.file.SimulateResultBinaryFileOutputStream
import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacterImplV0
import java.nio.file.Files
import java.nio.file.Path

@ExperimentalStdlibApi
fun main() {
    System.setProperty("log4j.configurationFile", "log4j_conf.xml")
    val uni = SimulatedUniverse(
        listOf(
            RoundtripCharacterImplV0("Test character 1", 100f, 25f, 95u),
            RoundtripCharacterImplV0("Test character 2", 200f, 15f, 105u),
            RoundtripCharacterImplV0("Test character 3", 50f, 50f, 105u),
            RoundtripCharacterImplV0("Test character 4", 150f, 20f, 125u)
        ),
        listOf(
            RoundtripCharacterImplV0("Test enemy 1", 50f * 1.5f, 20f * 1.5f, 60u),
            RoundtripCharacterImplV0("Test enemy 2", 75f * 1.5f, 20f * 1.5f, 65u)
        ),
        5, 3
    )
    val output = SimulateResultBinaryFileOutputStream(Files.newOutputStream(Path.of("result3.nbt")), Compressions.GZIP)

    val t = object: Thread("Record Thread") {
        override fun run() {
            while (true) {
                try { sleep(200) } catch (_: InterruptedException) { break }
                output.recStatus()
            }
        }
    }.apply { start() }
    output.writeRes(uni)
    t.interrupt()
    output.recStatus()
    output.close()
    /*val o = PrintStream(Files.newOutputStream(Path.of("testtext.txt")))
    val `in` = NBTInputTextStream(GZIPInputStream(Files.newInputStream(Path.of("result3.nbt"))), o)
    `in`.readCompoundTag()
    `in`.close()*/
}
package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.file.Compressions
import com.primogemstudio.advancedfmk.simulator.file.SimulateResultBinaryFileOutputStream
import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacterImplv0
import java.nio.file.Files
import java.nio.file.Path

@ExperimentalStdlibApi
fun main() {
    System.setProperty("log4j.configurationFile", "log4j_conf.xml")
    val uni = SimulatedUniverse(
        listOf(
            RoundtripCharacterImplv0("Test character 1", 100f, 25f),
            RoundtripCharacterImplv0("Test character 2", 200f, 15f),
            RoundtripCharacterImplv0("Test character 3", 50f, 50f),
            RoundtripCharacterImplv0("Test character 3", 150f, 20f)
        ),
        listOf(
            RoundtripCharacterImplv0("Test enemy 1", 50f * 1.5f, 20f * 1.5f),
            RoundtripCharacterImplv0("Test enemy 2", 75f * 1.5f, 20f * 1.5f)
        ),
        5, 3
    )
    val output = SimulateResultBinaryFileOutputStream(Files.newOutputStream(Path.of("result3.nbt")), Compressions.GZIP)

    val t = object: Thread("Record Thread") {
        override fun run() {
            while (true) {
                sleep(200)
                output.recStatus()
            }
        }
    }.apply { start() }
    output.writeRes(uni)
    t.interrupt()
    output.recStatus()
    output.close()
}
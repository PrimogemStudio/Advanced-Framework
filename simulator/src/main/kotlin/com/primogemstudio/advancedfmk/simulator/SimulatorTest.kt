package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.file.Compressions
import com.primogemstudio.advancedfmk.simulator.file.SimulateResultBinaryFileOutputStream
import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacterImplv0
import java.nio.file.Files
import java.nio.file.Path

@ExperimentalStdlibApi
fun main() {
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
    val output = SimulateResultBinaryFileOutputStream(Files.newOutputStream(Path.of("result.nbt")), Compressions.GZIP)

    val t = Thread.ofVirtual().start {
        while (true) {
            Thread.sleep(200)
            output.recStatus()
        }
    }
    output.writeRes(uni)
    t.interrupt()
    output.recStatus()
    output.close()
}
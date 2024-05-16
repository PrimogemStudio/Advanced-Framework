package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.bin.NBTOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.GZIPOutputStream

@ExperimentalStdlibApi
fun main() {
    /*val uni = SimulatedUniverse(
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
    val output = SimulateResultFileOutputStream(Files.newOutputStream(Path.of("result.txt")), Compressions.GZIP)

    val t = Thread.ofVirtual().start {
        while (true) {
            Thread.sleep(200)
            output.recStatus()
        }
    }
    output.simulate(uni)
    t.interrupt()
    output.recStatus()*/

    val out = NBTOutputStream(GZIPOutputStream(Files.newOutputStream(Path.of("test.nbt"))))
    out.writeCompoundTag("Test", mapOf(
        Pair("val1", listOf("Test"))
    ))
    out.close()
}
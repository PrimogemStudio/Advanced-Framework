package com.primogemstudio.advancedfmk.tests

import com.primogemstudio.advancedfmk.bin.NBTInputTextStream
import com.primogemstudio.advancedfmk.simulator.SimulatedUniverse
import com.primogemstudio.advancedfmk.simulator.file.Compressions
import com.primogemstudio.advancedfmk.simulator.file.SimulateResultBinaryFileOutputStream
import com.primogemstudio.advancedfmk.simulator.objects.CharacterObjectImpl
import com.primogemstudio.advancedfmk.simulator.objects.EnemyObjectImpl
import com.primogemstudio.advancedfmk.simulator.objects.constraints.ObjectWeakness.*
import org.junit.jupiter.api.*
import java.io.File
import java.io.PrintStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.GZIPInputStream

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class SimulatorTest {
    @Order(0)
    @Test
    fun binOutputTest() {
        val uni = SimulatedUniverse(
            listOf(
                CharacterObjectImpl("Test character 1", 100f, 25f, 95u, 0.05f, 0.5f, Physical),
                CharacterObjectImpl("Test character 2", 200f, 15f, 105u, 0.05f, 0.5f, Physical),
                CharacterObjectImpl("Test character 3", 50f, 50f, 105u, 0.05f, 0.5f, Quantum),
                CharacterObjectImpl("Test character 4", 150f, 20f, 125u, 0.05f, 0.5f, Imaginary)
            ),
            listOf(
                EnemyObjectImpl("Test enemy 1", 50f * 1.5f, 20f * 1.5f, 60u, mutableListOf(Physical), 2),
                EnemyObjectImpl("Test enemy 2", 75f * 1.5f, 20f * 1.5f, 65u, mutableListOf(Ice), 2)
            ),
            5, 3
        )
        val output = SimulateResultBinaryFileOutputStream(Files.newOutputStream(Path.of("tests/result.nbt")), Compressions.GZIP)

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
    }

    @Order(1)
    @Test
    fun binTranslate() {
        val o = PrintStream(Files.newOutputStream(Path.of("tests/result.txt")))
        val `in` = NBTInputTextStream(GZIPInputStream(Files.newInputStream(Path.of("tests/result.nbt"))), o)
        `in`.readCompoundTag()
        `in`.close()
    }

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun configureLogging() {
            System.setProperty("log4j.configurationFile", "configs/log4j_conf.xml")
        }

        @BeforeAll
        @JvmStatic
        internal fun mkTestDir() {
            File("tests").deleteRecursively()
            File("tests").mkdirs()
        }
    }
}
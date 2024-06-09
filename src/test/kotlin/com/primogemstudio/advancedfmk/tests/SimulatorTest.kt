package com.primogemstudio.advancedfmk.tests

import com.primogemstudio.advancedfmk.bin.NBTInputTextStream
import com.primogemstudio.advancedfmk.simulator.starrailike.SimulatedUniverse
import com.primogemstudio.advancedfmk.simulator.starrailike.file.Compressions
import com.primogemstudio.advancedfmk.simulator.starrailike.file.SimulateResultBinaryFileOutputStream
import com.primogemstudio.advancedfmk.simulator.starrailike.objects.CharacterObjectImpl
import com.primogemstudio.advancedfmk.simulator.starrailike.objects.EnemyObjectImpl
import com.primogemstudio.advancedfmk.simulator.starrailike.objects.constraints.ObjectWeakness.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.junit.jupiter.api.*
import java.io.File
import java.io.PrintStream
import java.lang.Thread.sleep
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.GZIPInputStream
import java.util.zip.InflaterInputStream

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class SimulatorTest {
    @OptIn(DelicateCoroutinesApi::class)
    internal fun binOutput(c: Compressions, target: String) {
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
        val output = SimulateResultBinaryFileOutputStream(Files.newOutputStream(Path.of(target)), c)
        var ended = false
        GlobalScope.async {
            while (!ended) {
                try { sleep(200) } catch (_: InterruptedException) { break }
                output.recStatus()
            }
        }.onAwait

        output.writeRes(uni)
        ended = true
        output.recStatus()
        output.close()
    }

    @Order(0)
    @Test
    fun binOutputGzipTest() {
        binOutput(Compressions.GZIP, "tests/result.nbt")
    }
    @Order(0)
    @Test
    fun binOutputDeflaterTest() {
        binOutput(Compressions.DEFLATER, "tests/result_def.nbt")
    }

    @Order(1)
    @Test
    fun binTranslateGzip() {
        val o = PrintStream(Files.newOutputStream(Path.of("tests/result.txt")))
        val `in` = NBTInputTextStream(GZIPInputStream(Files.newInputStream(Path.of("tests/result.nbt"))), o)
        `in`.readCompoundTag()
        `in`.close()
    }

    @Order(1)
    @Test
    fun binTranslateInflater() {
        val o = PrintStream(Files.newOutputStream(Path.of("tests/result_def.txt")))
        val `in` = NBTInputTextStream(InflaterInputStream(Files.newInputStream(Path.of("tests/result_def.nbt"))), o)
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
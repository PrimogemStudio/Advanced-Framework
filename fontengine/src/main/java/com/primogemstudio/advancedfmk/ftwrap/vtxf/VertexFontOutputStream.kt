package com.primogemstudio.advancedfmk.ftwrap.vtxf

import com.primogemstudio.advancedfmk.client.LOGGER
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeGlyph
import com.primogemstudio.advancedfmk.ftwrap.SVGQueue
import kotlinx.coroutines.*
import java.io.DataOutputStream
import java.io.OutputStream
import java.lang.Thread.sleep

class VertexFontOutputStream(out: OutputStream, private val ttf: FreeTypeFont) : DataOutputStream(out) {
    @OptIn(DelicateCoroutinesApi::class)
    fun write() {
        writeUTF("VTXF")
        writeShort(0x0307)
        writeByte(0)

        var l: Int
        var le = 0
        var lc = 0
        // val map = mutableListOf<Pair<Char, FreeTypeGlyph>>()
        val indeterminate = mutableMapOf<Char, SVGQueue>()

        ttf.getAllChars().apply { l = size }.forEach {
            indeterminate[it] = ttf.fetchGlyphOutline(it.code.toLong())
        }

        /*runBlocking {
            val jobs = mutableListOf<Job>()
            indeterminate.forEach { (t, u) ->
                jobs.add(GlobalScope.launch(context = Dispatchers.IO) {
                    map.add(Pair(t, u.toVertices(25).bake()))
                    le++
                })
                sleep(1)
                lc++
                if (lc % 100 == 0) LOGGER.info("${lc.toFloat() / l.toFloat() * 100} % committed")
            }

            while (le < l) {
                val let = mutableListOf<Job>()
                jobs.forEach {
                    if (it.isCompleted) let.add(it)
                }
                jobs.removeAll(let)

                sleep(1000)
                LOGGER.info("${le.toFloat() / l.toFloat() * 100} % complete")
            }
        }*/

        le = 0
        writeInt(l)

        for (a in indeterminate.entries) {
            val glyph = a.value.toVertices(25).bake()
            writeInt(a.key.code)
            writeFloat(glyph.whscale)

            writeInt(glyph.vertices.size)
            glyph.vertices.forEach {
                writeFloat(it.x)
                writeFloat(it.y)
            }
            writeInt(glyph.indices.size)
            glyph.indices.forEach { writeInt(it) }

            le++
            if (le % 250 == 0) LOGGER.info("${a.key} 0x${Integer.toHexString(a.key.code)} ${le.toFloat() / l.toFloat() * 100} % complete")
        }

        writeLong(0x20230426 * l.toLong())
    }
}
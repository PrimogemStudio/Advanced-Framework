package com.primogemstudio.advancedfmk.ftwrap.vtxf

import com.primogemstudio.advancedfmk.client.LOGGER
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeGlyph
import com.primogemstudio.advancedfmk.ftwrap.SVGQueue
import kotlinx.coroutines.*
import java.io.DataOutputStream
import java.io.OutputStream
import java.lang.Thread.sleep

class VertexFontOutputStream(out: OutputStream, val ttf: FreeTypeFont) : DataOutputStream(out) {
    @OptIn(DelicateCoroutinesApi::class)
    fun write() {
        writeUTF("VTXF")
        writeByte(0)

        var l: Int
        var le = 0
        val map = mutableMapOf<Char, FreeTypeGlyph>()
        val indeterminate = mutableMapOf<Char, SVGQueue>()

        ttf.getAllChars().apply { l = this.size }.forEach {
            indeterminate[it] = ttf.fetchGlyphOutline(it.code.toLong())
        }

        runBlocking {
            val jobs = mutableListOf<Job>()
            indeterminate.forEach { (t, u) ->
                jobs.add(GlobalScope.launch(context = Dispatchers.IO) {
                    map[t] = u.toVertices(25).bake()
                    le++
                })
            }
            jobs.forEach {
                while (!it.isCompleted) {
                    sleep(1000)
                    LOGGER.info("${le.toFloat() / l.toFloat() * 100} % complete")
                }
            }
        }

        le = 0
        map.forEach { (c, glyph) ->
            writeInt(c.code)
            writeFloat(glyph.whscale)

            writeInt(glyph.vertices.size)
            glyph.vertices.forEach {
                writeFloat(it.x)
                writeFloat(it.y)
            }
            writeInt(glyph.indices.size)
            glyph.indices.forEach { writeInt(it) }

            le++
            LOGGER.info("$c 0x${Integer.toHexString(c.code)} ${le.toFloat() / l.toFloat() * 100} % complete")
        }

        writeLong(0x20230426 * l.toLong())
        writeShort(0x0307)
    }
}
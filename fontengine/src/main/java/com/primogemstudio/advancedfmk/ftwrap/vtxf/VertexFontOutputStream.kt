package com.primogemstudio.advancedfmk.ftwrap.vtxf

import com.primogemstudio.advancedfmk.client.LOGGER
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import com.primogemstudio.advancedfmk.ftwrap.SVGQueue
import java.io.DataOutputStream
import java.io.OutputStream

class VertexFontOutputStream(out: OutputStream, private val ttf: FreeTypeFont) : DataOutputStream(out) {
    fun write() {
        writeUTF("VTXF")
        writeShort(0x0307)
        writeByte(0)

        var l: Int
        var le = 0
        val indeterminate = mutableMapOf<Char, SVGQueue>()

        ttf.getAllChars().apply { l = size }.forEach {
            indeterminate[it] = ttf.fetchGlyphOutline(it.code.toLong())
        }

        writeInt(l)

        for (a in indeterminate.entries) {
            val glyph = a.value.toVertices(15).bake()
            writeInt(a.key.code)
            writeFloat(glyph.whscale)

            writeInt(glyph.vertices.size)
            glyph.vertices.forEach {
                writeFloat(it.x)
                writeFloat(it.y)
            }
            writeInt(glyph.indices.size)
            glyph.indices.reversed().forEach { writeInt(it) }

            le++
            if (le % 250 == 0) LOGGER.info("${a.key} 0x${Integer.toHexString(a.key.code)} ${le.toFloat() / l.toFloat() * 100} % complete")
        }

        writeLong(0x20230426 * l.toLong())
    }
}
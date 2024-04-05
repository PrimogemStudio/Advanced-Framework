package com.primogemstudio.advancedfmk.ftwrap.vtxf

import com.primogemstudio.advancedfmk.ftwrap.FreeTypeGlyph
import java.io.DataInputStream
import java.io.InputStream

class VertexFontInputStream(`in`: InputStream): DataInputStream(`in`) {
    fun parse(): List<Pair<Char, FreeTypeGlyph>> {
        if (readShort() != 0x0307.toShort()) throw IllegalStateException("Font header wrong")
        val version = readByte()
        val glyph = readInt()
        println("Glyphs: $glyph (ver $version)")

        return listOf()
    }
}
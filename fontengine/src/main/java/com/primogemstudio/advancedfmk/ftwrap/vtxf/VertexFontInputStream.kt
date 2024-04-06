package com.primogemstudio.advancedfmk.ftwrap.vtxf

import com.primogemstudio.advancedfmk.client.LOGGER
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeGlyph
import org.joml.Vector2f
import java.io.DataInputStream
import java.io.EOFException
import java.io.InputStream

class VertexFontInputStream(`in`: InputStream): DataInputStream(`in`) {
    fun parse(): List<Pair<Char, FreeTypeGlyph>> {
        if (readUTF() != "VTXF") throw IllegalStateException("Font header wrong")
        if (readShort() != 0x0307.toShort()) throw IllegalStateException("Magic number wrong")
        val version = readByte()
        val glyph = readInt()
        println("Glyphs: $glyph (ver $version)")

        val map = mutableListOf<Pair<Char, FreeTypeGlyph>>()
        var le = 0
        for (i in 0 ..< glyph) {
            val code = readInt()
            val whscale = readFloat()

            val verticesSize = try {
                readInt()
            }
            catch (e: Exception) {
                println(i)
                break
            }
            val vertices = mutableListOf<Vector2f>()

            for (j in 0 ..< verticesSize) {
                vertices.add(Vector2f(readFloat(), readFloat()))
            }

            val indicesSize = readInt()
            val indices = mutableListOf<Int>()
            if (indicesSize % 3 != 0) throw IllegalStateException("Wrong indices size")

            for (k in 0 ..< indicesSize) {
                indices.add(readInt())
            }

            map.add(Pair(code.toChar(), FreeTypeGlyph(whscale, vertices, indices)))
            le++
            if (le % 250 == 0) LOGGER.info("${code.toChar()} 0x${Integer.toHexString(code)} ${le.toFloat() / glyph.toFloat() * 100} % complete")
        }
        if (readLong() / glyph.toLong() != 0x20230426L) throw IllegalStateException("Wrong file end")

        return map
    }
}
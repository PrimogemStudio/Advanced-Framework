package com.primogemstudio.advancedfmk.ftwrap

import org.lwjgl.system.MemoryStack
import org.lwjgl.util.freetype.FT_Face
import org.lwjgl.util.freetype.FT_Matrix
import org.lwjgl.util.freetype.FT_Vector
import org.lwjgl.util.freetype.FreeType.*
import java.io.Closeable
import java.io.InputStream
import java.nio.ByteBuffer

class FreeTypeFont: Closeable {
    companion object {
        const val multiplier = 65536L
    }
    var face: FT_Face?
    var pLib: Long = 0L
    constructor(`in`: InputStream) {
        var pFace: Long
        MemoryStack.stackPush().use { stack ->
            val ptrBuff = stack.mallocPointer(1)
            FT_Init_FreeType(ptrBuff)
            pLib = ptrBuff.get(0)

            val br = `in`.readAllBytes()
            val bf = ByteBuffer.allocateDirect(br.size)
            bf.put(br)

            FT_New_Memory_Face(pLib, bf, 0, ptrBuff)
            pFace = ptrBuff.get(0)
        }
        face = FT_Face.create(pFace)
        initFontState()
    }
    constructor(path: String) {
        var pFace: Long
        MemoryStack.stackPush().use { stack ->
            val ptrBuff = stack.mallocPointer(1)
            FT_Init_FreeType(ptrBuff)
            pLib = ptrBuff.get(0)
            FT_New_Face(
                pLib,
                "/usr/share/fonts/StarRailFont.ttf",
                0,
                ptrBuff
            )
            pFace = ptrBuff.get(0)
        }
        face = FT_Face.create(pFace)
        initFontState()
    }

    private fun initFontState() {
        FT_Set_Pixel_Sizes(face!!, 0, 12)
        FT_Set_Transform(
            face!!,
            FT_Matrix.create()
                .xx(1L * multiplier)
                .xy(0L * multiplier)
                .yx(0L * multiplier)
                .yy(-1L * multiplier),

            FT_Vector.create()
                .x(0L)
                .y(0L)
        )
    }

    fun getAllChars(): List<Long> {
        val result = mutableListOf<Long>()
        MemoryStack.stackPush().use {
            val s = it.mallocInt(1)
            var l = FT_Get_First_Char(face!!, s)
            while (s.get(0) != 0) {
                result.add(l)
                l = FT_Get_Next_Char(face!!, l, s)
            }
        }
        return result
    }

    override fun close() {
        FT_Done_Face(face!!)
        FT_Done_FreeType(pLib)
    }
}
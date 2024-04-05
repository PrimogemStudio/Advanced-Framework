package com.primogemstudio.advancedfmk.ftwrap

import com.google.common.collect.ImmutableMap
import com.primogemstudio.advancedfmk.util.i26p6tof
import org.joml.Vector2f
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.lwjgl.util.freetype.FT_Face
import org.lwjgl.util.freetype.FT_Outline_Funcs
import org.lwjgl.util.freetype.FT_Vector
import org.lwjgl.util.freetype.FreeType.*
import java.io.Closeable
import java.io.InputStream
import java.nio.ByteBuffer

fun addrToVec(addr: Long): Vector2f {
    val vec = FT_Vector.create(addr)
    return Vector2f(i26p6tof(vec.x().toInt()), i26p6tof(vec.y().toInt()))
}

object FreeTypeLibrary {
    val handle: Long

    init {
        MemoryStack.stackPush().use { stack ->
            val ptrBuff = stack.mallocPointer(1)
            FT_Init_FreeType(ptrBuff)
            handle = ptrBuff.get()
        }
    }
}

class FreeTypeFont : Closeable {
    companion object {
        val functions = { ops: MutableList<SVGOperation> ->
            FT_Outline_Funcs.create().move_to { to, _ ->
                ops.add(
                    SVGOperation(
                        type = SVGOperation.OpType.MOVE, target = addrToVec(to)
                    )
                ); 0
            }.line_to { to, _ ->
                ops.add(
                    SVGOperation(
                        type = SVGOperation.OpType.LINE, target = addrToVec(to)
                    )
                ); 0
            }.conic_to { ct, to, _ ->
                ops.add(
                    SVGOperation(
                        type = SVGOperation.OpType.CONIC, target = addrToVec(to), control1 = addrToVec(ct)
                    )
                ); 0
            }.cubic_to { ct1, ct2, to, _ ->
                ops.add(
                    SVGOperation(
                        type = SVGOperation.OpType.CONIC,
                        target = addrToVec(to),
                        control1 = addrToVec(ct1),
                        control2 = addrToVec(ct2)
                    )
                ); 0
            }
        }
    }

    private var face: FT_Face
    private var buff: ByteBuffer? = null

    constructor(ins: InputStream) {
        val br = ins.readAllBytes()
        ins.close()
        buff = MemoryUtil.memAlloc(br.size)
        buff!!.put(br)
        buff!!.position(0)
        MemoryStack.stackPush().use { stack ->
            val ptrBuff = stack.mallocPointer(1)
            FT_New_Memory_Face(
                FreeTypeLibrary.handle, buff!!, 0, ptrBuff
            )
            face = FT_Face.create(ptrBuff.get())
            initFontState()
        }
    }

    constructor(path: String) {
        MemoryStack.stackPush().use { stack ->
            val ptrBuff = stack.mallocPointer(1)
            FT_New_Face(FreeTypeLibrary.handle, path, 0, ptrBuff)
            face = FT_Face.create(ptrBuff.get())
            initFontState()
        }
    }

    private fun initFontState() {
        FT_Set_Pixel_Sizes(face, 0, 12)
    }

    fun getAllChars(): Map<Char, Int> {
        val map = ImmutableMap.builder<Char, Int>()
        MemoryStack.stackPush().use {
            val index = it.mallocInt(1)
            var chr = FT_Get_First_Char(face, index).toInt().toChar()
            while (index.get(0) != 0) {
                map.put(chr, index.get(0))
                chr = FT_Get_Next_Char(face, chr.code.toLong(), index).toInt().toChar()
            }
        }
        return map.build()
    }

    override fun close() {
        FT_Done_Face(face)
        MemoryUtil.memFree(buff)
    }

    fun fetchGlyphOutline(chr: Long): SVGQueue {
        val target = SVGQueue()
        val glyphIndex = FT_Get_Char_Index(face, chr)
        FT_Load_Glyph(face, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
        val outline = face.glyph()?.outline()!!
        FT_Outline_Decompose(outline, functions(target), 1)
        val border = fetchGlyphBorder(chr)

        target.forEach {
            it.target.div(border).apply { y = 1 - y }
            it.control1?.div(border)?.apply { y = 1 - y }
            it.control2?.div(border)?.apply { y = 1 - y }
        }

        return target
    }

    fun fetchGlyphBorder(chr: Long): Vector2f {
        val glyphIndex = FT_Get_Char_Index(face, chr)
        FT_Load_Glyph(face, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
        val metrics = face.glyph()?.metrics()!!

        return Vector2f(i26p6tof(metrics.width().toInt()), i26p6tof(metrics.height().toInt()))
    }

    fun fetchGlyphBorderf(chr: Long): Float {
        return fetchGlyphBorder(chr).let { it.x / it.y }
    }
}
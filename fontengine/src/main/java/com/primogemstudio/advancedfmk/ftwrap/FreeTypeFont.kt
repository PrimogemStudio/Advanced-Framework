package com.primogemstudio.advancedfmk.ftwrap

import com.primogemstudio.advancedfmk.util.i26p6tof
import org.joml.Matrix2f
import org.joml.Vector2f
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.lwjgl.util.freetype.FT_Face
import org.lwjgl.util.freetype.FT_Outline_Funcs
import org.lwjgl.util.freetype.FT_Vector
import org.lwjgl.util.freetype.FreeType.*
import java.io.Closeable
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

    constructor(data: ByteArray) {
        buff = MemoryUtil.memAlloc(data.size)
        buff!!.put(data)
        buff!!.rewind()
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

    fun getAllChars(): List<Char> {
        val map = mutableListOf<Char>()
        MemoryStack.stackPush().use {
            val index = it.mallocInt(1)
            var chr = FT_Get_First_Char(face, index).toInt().toChar()
            while (index.get(0) != 0) {
                map.add(chr)
                chr = FT_Get_Next_Char(face, chr.code.toLong(), index).toInt().toChar()
                if (map.contains(chr)) break
            }
        }
        return map
    }

    override fun close() {
        FT_Done_Face(face)
        MemoryUtil.memFree(buff)
    }

    fun fetchGlyphOutline(chr: Long): SVGQueue? {
        val glyphIndex = FT_Get_Char_Index(face, chr)
        if (glyphIndex == 0) return null
        FT_Load_Glyph(face, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
        val outline = face.glyph()?.outline()!!
        val border = fetchGlyphBorder(chr)!!
        val target = SVGQueue(border)
        FT_Outline_Decompose(outline, functions(target), 1)

        target.forEach {
            it.target.div(border).mul(Matrix2f(1f, 0f, 0f, -1f)).mul(16f)
            it.control1?.div(border)?.mul(Matrix2f(1f, 0f, 0f, -1f))?.mul(16f)
            it.control2?.div(border)?.mul(Matrix2f(1f, 0f, 0f, -1f))?.mul(16f)
        }
        return target
    }

    fun fetchGlyphBorder(chr: Long): Vector2f? {
        val glyphIndex = FT_Get_Char_Index(face, chr)
        if (glyphIndex == 0) return null
        FT_Load_Glyph(face, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
        val metrics = face.glyph()?.metrics()!!
        return Vector2f(i26p6tof(metrics.width().toInt()), i26p6tof(metrics.height().toInt()))
    }
}
package com.primogemstudio.advancedfmk.ftwrap

import com.primogemstudio.advancedfmk.util.f26p6toi
import com.primogemstudio.advancedfmk.util.i26p6tof
import org.joml.Vector2f
import org.lwjgl.system.MemoryStack
import org.lwjgl.util.freetype.FT_Face
import org.lwjgl.util.freetype.FT_Matrix
import org.lwjgl.util.freetype.FT_Outline_Funcs
import org.lwjgl.util.freetype.FT_Vector
import org.lwjgl.util.freetype.FreeType.*
import java.io.Closeable
import java.io.InputStream

fun addrToVec(addr: Long): Vector2f {
    val vec = FT_Vector.create(addr)
    return Vector2f(i26p6tof(vec.x().toInt()), i26p6tof(vec.y().toInt()))
}
class FreeTypeFont: Closeable {
    companion object {
        val funcs = { oplist: MutableList<SVGOperation> ->
            FT_Outline_Funcs.create()
                .move_to { to, _ ->
                    oplist.add(
                        SVGOperation(
                            type = SVGOperation.OpType.MOVE,
                            target = addrToVec(to)
                        )
                    ); 0
                }
                .line_to { to, _ ->
                    oplist.add(
                        SVGOperation(
                            type = SVGOperation.OpType.LINE,
                            target = addrToVec(to)
                        )
                    ); 0
                }
                .conic_to { ct, to, _ ->
                    oplist.add(
                        SVGOperation(
                            type = SVGOperation.OpType.CONIC,
                            target = addrToVec(to),
                            control1 = addrToVec(ct)
                        )
                    ); 0
                }
                .cubic_to { ct1, ct2, to, _ ->
                    oplist.add(
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
    var face: FT_Face?
    var pLib: Long = 0L
    constructor(`in`: InputStream) {
        var pFace: Long
        MemoryStack.stackPush().use { stack ->
            val ptrBuff = stack.mallocPointer(1)
            FT_Init_FreeType(ptrBuff)
            pLib = ptrBuff.get(0)

            val br = `in`.readAllBytes()
            val bf = stack.malloc(br.size)
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
                path,
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

    fun fetchGlyphOutline(charcode: Long): SVGQueue {
        val target = SVGQueue()
        val glyphIndex = FT_Get_Char_Index(face!!, charcode)
        FT_Load_Glyph(face!!, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
        val outline = face!!.glyph()?.outline()!!
        FT_Outline_Decompose(outline, funcs(target), 1)
        val border = fetchGlyphBorder(charcode)

        target.forEach {
            it.target.div(border).apply { y = 1 - y }
            it.control1?.div(border)?.apply { y = 1 - y }
            it.control2?.div(border)?.apply { y = 1 - y }
        }

        return target
    }

    fun fetchGlyphBorder(charcode: Long): Vector2f {
        val glyphIndex = FT_Get_Char_Index(face!!, charcode)
        FT_Load_Glyph(face!!, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
        val metrics = face!!.glyph()?.metrics()!!

        return Vector2f(i26p6tof(metrics.width().toInt()), i26p6tof(metrics.height().toInt()))
    }
}
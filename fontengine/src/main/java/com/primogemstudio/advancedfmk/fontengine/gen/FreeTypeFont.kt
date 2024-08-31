package com.primogemstudio.advancedfmk.fontengine.gen

import com.primogemstudio.advancedfmk.util.i26p6tof
import net.minecraft.client.gui.font.providers.FreeTypeUtil
import org.joml.Matrix2f
import org.joml.Vector2f
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.MemoryUtil.memAlloc
import org.lwjgl.util.freetype.FT_Face
import org.lwjgl.util.freetype.FT_Outline_Funcs
import org.lwjgl.util.freetype.FT_Vector
import org.lwjgl.util.freetype.FreeType.*
import org.lwjgl.util.harfbuzz.HarfBuzz.*
import org.lwjgl.util.harfbuzz.hb_feature_t
import java.io.Closeable
import java.nio.ByteBuffer
import kotlin.math.max

fun addrToVec(addr: Long): Vector2f {
    val vec = FT_Vector.create(addr)
    return Vector2f(i26p6tof(vec.x().toInt()), i26p6tof(vec.y().toInt()))
}

object FreeTypeLibrary {
    val handle = FreeTypeUtil.getLibrary()
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
    private var hb_blob: Long = 0L
    private var hb_face: Long = 0L
    private var hb_upem = 0
    private var hb_font: Long = 0L

    constructor(data: ByteArray) {
        buff = memAlloc(data.size)
        buff!!.put(data)
        buff!!.rewind()
        MemoryStack.stackPush().use { stack ->
            val ptrBuff = stack.mallocPointer(1)
            FT_New_Memory_Face(
                FreeTypeLibrary.handle, buff!!, 0, ptrBuff
            )
            face = FT_Face.create(ptrBuff.get())
            hb_blob = hb_blob_create(buff!!, HB_MEMORY_MODE_READONLY, ptrBuff.address()) {}
            initFontState()
        }
    }

    constructor(path: String) {
        MemoryStack.stackPush().use { stack ->
            val ptrBuff = stack.mallocPointer(1)
            FT_New_Face(FreeTypeLibrary.handle, path, 0, ptrBuff)
            face = FT_Face.create(ptrBuff.get())
            hb_blob = hb_blob_create_from_file_or_fail(path)
            initFontState()
        }
    }

    private fun initFontState() {
        FT_Set_Pixel_Sizes(face, 0, 8)

        hb_face = hb_face_create(hb_blob, 0)
        hb_upem = hb_face_get_upem(hb_face)
        hb_font = hb_font_create(hb_face)
        hb_font_set_scale(hb_font, hb_upem, hb_upem)
        hb_ft_font_set_funcs(hb_font)
    }

    override fun close() {
        FT_Done_Face(face)
        MemoryUtil.memFree(buff)
        hb_face_destroy(hb_face)
        hb_font_destroy(hb_font)
        hb_blob_destroy(hb_blob)
    }

    fun fetchGlyphOutline(chr: Long, raw: Boolean = false): SVGQueue? {
        val c = if (raw) chr.toInt() else FT_Get_Char_Index(face, chr)
        if (c == 0) return null
        FT_Load_Glyph(face, c, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP or FT_LOAD_VERTICAL_LAYOUT)
        val outline = face.glyph()?.outline()!!
        val border = fetchGlyphBorder(c)
        val target = SVGQueue(border)
        FT_Outline_Decompose(outline, functions(target), 1)

        target.forEach {
            it.target.div(border).mul(Matrix2f(1f, 0f, 0f, -1f))
            it.control1?.div(border)?.mul(Matrix2f(1f, 0f, 0f, -1f))
            it.control2?.div(border)?.mul(Matrix2f(1f, 0f, 0f, -1f))
        }
        return target
    }

    private fun fetchGlyphBorder(c: Int): Vector2f {
        FT_Load_Glyph(face, c, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
        val metrics = face.glyph()?.metrics()!!
        return Vector2f(
            max(i26p6tof(metrics.horiAdvance().toInt()), i26p6tof(metrics.width().toInt())),
            max(i26p6tof(metrics.vertAdvance().toInt()), i26p6tof(metrics.height().toInt()))
        )
    }

    private var shapingFeatures: List<Pair<String, Boolean>> = listOf(
        "liga", "calm", "ss01", "ss02", "ss03", "ss04", "ss05", "ss06", "ss07", "ss08", "ss09", "cv30", "cv60", "cv61"
    ).map { Pair(it, true) }

    private fun loadFeaturesToMem(stack: MemoryStack): hb_feature_t.Buffer {
        val genF = { t: String -> HB_TAG(t[0].code, t[1].code, t[2].code, t[3].code) }

        val shapes = hb_feature_t.malloc(16, stack)
        var i = 0
        shapingFeatures.forEach {
            shapes[i].tag(genF(it.first))
            shapes[i].value(if (it.second) 1 else 0)
            shapes[i].start(HB_FEATURE_GLOBAL_START)
            shapes[i].end(HB_FEATURE_GLOBAL_END)
            i++
        }

        return shapes
    }

    fun shape(s: String): Pair<Array<Pair<Int, Vector2f>>, Int> {
        val buffer = hb_buffer_create()
        hb_buffer_add_utf8(buffer, s, 0, -1)
        hb_buffer_guess_segment_properties(buffer)
        val direction = hb_buffer_get_direction(buffer)

        MemoryStack.stackPush().use { stack ->
            hb_shape(hb_font, buffer, loadFeaturesToMem(stack))
        }

        val count = hb_buffer_get_length(buffer)
        val infos = hb_buffer_get_glyph_infos(buffer)
        val positions = hb_buffer_get_glyph_positions(buffer)
        val result = mutableListOf<Pair<Int, Vector2f>>()
        for (i in 0..<count) {
            result.add(
                Pair(
                    infos?.get(i)?.codepoint() ?: 0, Vector2f(
                        positions?.get(i)?.x_offset()?.toFloat()?.div(hb_upem) ?: 0f,
                        positions?.get(i)?.y_offset()?.toFloat()?.div(hb_upem) ?: 0f
                    )
                )
            )
        }
        hb_buffer_destroy(buffer)

        return Pair(result.toTypedArray(), direction)
    }

    fun getGlyphId(chr: Long): Int = FT_Get_Char_Index(face, chr)
    fun getGlyphName(t: Int): String {
        val buff = memAlloc(128)
        buff.rewind()
        FT_Get_Glyph_Name(face, t, buff)
        val dest = ByteArray(128)
        buff.get(dest)
        return String(dest.slice(0..<dest.indexOfFirst { it == 0x00.toByte() }).toByteArray())
    }
}
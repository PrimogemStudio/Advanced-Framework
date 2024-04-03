package com.primogemstudio.advancedfmk.client

import org.lwjgl.system.MemoryStack
import org.lwjgl.util.freetype.FT_Face
import org.lwjgl.util.freetype.FT_Vector
import org.lwjgl.util.freetype.FreeType.*
import java.io.File
import java.io.PrintStream
import java.nio.file.Files
import kotlin.math.pow

fun main() {
    fun int26p6_to_float(i: Int): Float {
        return i.toFloat() / (2.0.pow(6).toFloat())
    }
    fun int12p12_to_float(i: Int): Float {
        return i.toFloat() / (2.0.pow(12).toFloat())
    }

    var pLib: Long
    var pFace: Long
    MemoryStack.stackPush().use { stack ->
        val ptrBuff = stack.mallocPointer(1)
        FT_Init_FreeType(ptrBuff)
        pLib = ptrBuff.get(0)
        FT_New_Face(pLib, "/usr/share/fonts/StarRailFont.ttf", 0, ptrBuff)
        pFace = ptrBuff.get(0)
    }
    val fce = FT_Face.create(pFace)
    FT_Set_Pixel_Sizes(fce, 0, 24)
    val glyphIndex = FT_Get_Char_Index(fce, 'j'.code.toLong())
    FT_Load_Glyph(fce, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
    val outline = fce.glyph()?.outline()!!
    println(outline.n_points())

    val f = File("output.csv")
    f.delete()
    f.createNewFile()
    val ps = PrintStream(f)
    for (a in outline.points()) {
        ps.println("${a.x()},${a.y()}")
    }

    var limit = 0
    var v_last = FT_Vector.create()
    var v_control: FT_Vector
    var v_start: FT_Vector

    var point = 0
    var first = 0
    for (i in 0 ..< outline.n_contours()) {
        val last = outline.contours()[i].toInt()
        limit = i

        v_start = outline.points()[first]
        v_last = outline.points()[last]
        v_control = v_start

        point = first
        val tags = outline.tags()[first]
        var tag = FT_CURVE_TAG(tags.toInt())

        val fpriX = int26p6_to_float(v_control.x().toInt())
        val fpriY = -int26p6_to_float(v_control.y().toInt())

        var startX = fpriX
        var startY = fpriY

        println("$fpriX, $fpriY")
        while (point < limit) {
            point++
            limit++
            tag = FT_CURVE_TAG(tags.toInt())
            println(tag)
            when (tag) {
                FT_CURVE_TAG_ON -> {
                    val pointd = outline.points()[point]
                    val fEndX = int26p6_to_float(pointd.x().toInt())
                    val fEndY = -int26p6_to_float(pointd.y().toInt())

                    println("lineto: $startX, $startY, $fEndX, $fEndY")

                    startX = fEndX
                    startY = fEndY
                }
            }
        }

        first = last + 1
    }
}
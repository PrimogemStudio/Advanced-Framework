package com.primogemstudio.advancedfmk.client

import org.joml.Vector2f
import org.lwjgl.system.MemoryStack
import org.lwjgl.util.freetype.FT_Face
import org.lwjgl.util.freetype.FT_Outline_Funcs
import org.lwjgl.util.freetype.FT_Vector
import org.lwjgl.util.freetype.FreeType.*
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.pow

fun main() {
    fun i26p6tof(i: Int): Float {
        return i.toFloat() * (2.0.pow(-6).toFloat())
    }
    fun addrToVec(addr: Long): Vector2f {
        val ft_vec = FT_Vector.create(addr)
        return Vector2f(i26p6tof(ft_vec.x().toInt()), i26p6tof(ft_vec.y().toInt()))
    }

    var pLib: Long
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
    val fce = FT_Face.create(pFace)
    FT_Set_Pixel_Sizes(fce, 0, 24)
    val glyphIndex = FT_Get_Char_Index(fce, 'j'.code.toLong())
    FT_Load_Glyph(fce, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
    val outline = fce.glyph()?.outline()!!
    val funcs = FT_Outline_Funcs.create()
        .move_to { to, user -> println("moveto: ${addrToVec(to)}"); 0}
        .line_to { to, user -> println("lineto: ${addrToVec(to)}"); 0 }
        .conic_to { ct, to, user -> println("conicto: ${addrToVec(ct)}, ${addrToVec(to)}"); 0 }
        .cubic_to { ct1, ct2, to, user -> println("cubicto: ${addrToVec(ct1)}, ${addrToVec(ct2)}, ${addrToVec(to)}"); 0 }
    FT_Outline_Decompose(outline, funcs, 1)

    FT_Done_Face(fce)
    FT_Done_FreeType(pLib)
}
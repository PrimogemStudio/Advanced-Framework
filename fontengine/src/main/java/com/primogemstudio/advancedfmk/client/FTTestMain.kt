package com.primogemstudio.advancedfmk.client

import org.lwjgl.system.MemoryStack
import org.lwjgl.util.freetype.FT_Face
import org.lwjgl.util.freetype.FreeType.*

fun main() {
    var pLib: Long
    var pFace: Long
    MemoryStack.stackPush().use { stack ->
        val ptrBuff = stack.mallocPointer(1)
        FT_Init_FreeType(ptrBuff)
        pLib = ptrBuff.get(0)
        FT_New_Face(pLib, "C:/Users/Hacker/Desktop/zh-cn.ttf", 0, ptrBuff)
        pFace = ptrBuff.get(0)
    }
    val fce = FT_Face.create(pFace)
    FT_Set_Pixel_Sizes(fce, 0, 24)
    val glyphIndex = FT_Get_Char_Index(fce, 'i'.code.toLong())
    FT_Load_Glyph(fce, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
    val outline = fce.glyph()?.outline()!!
    println(outline.n_points())
    for (a in outline.points()) {
        print("[${a.x()},${a.y()}]")
    }
}
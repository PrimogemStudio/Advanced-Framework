package com.primogemstudio.advancedfmk.client

import org.lwjgl.PointerBuffer
import org.lwjgl.util.freetype.FT_Face
import java.nio.ByteBuffer
import org.lwjgl.util.freetype.FreeType.*

fun main() {
    val lib = PointerBuffer.create(ByteBuffer.allocate(2048))
    val face = PointerBuffer.create(ByteBuffer.allocate(2048))
    FT_Init_FreeType(lib)
    FT_New_Face(lib.address(), "/usr/share/fonts/StarRailFont.ttf", 0, face)
    val fce = FT_Face.create(face.address())
    FT_Set_Pixel_Sizes(fce, 0, 24)

    val glyphIndex = FT_Get_Char_Index(fce, 'i'.code.toLong())
    FT_Load_Glyph(fce, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
    val outline = fce.glyph()?.outline()
    println(outline?.n_points())
    println(outline?.points())
}
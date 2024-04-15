package com.primogemstudio.advancedfmk.ftwrap

import com.primogemstudio.advancedfmk.util.Compressor

object DefaultFont {
    val FONT = FreeTypeFont(
        Compressor.decode(DefaultFont.javaClass.getResourceAsStream("/star_rail.res")!!.readAllBytes())
    )
}
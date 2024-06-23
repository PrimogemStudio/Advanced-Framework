package com.primogemstudio.advancedfmk.fontengine

import com.primogemstudio.advancedfmk.fontengine.gen.FreeTypeFont
import com.primogemstudio.advancedfmk.util.Compressor

object DefaultFont {
    val FONT = FreeTypeFont(
        Compressor.decode(DefaultFont.javaClass.getResourceAsStream("/star_rail.res")!!.readAllBytes())
    )

    val ABRAIC = FreeTypeFont(
        Compressor.decode(DefaultFont.javaClass.getResourceAsStream("/abraic.res")!!.readAllBytes())
    )
}
package com.primogemstudio.advancedfmk.fontengine

import com.primogemstudio.advancedfmk.fontengine.gen.FreeTypeFont
import com.primogemstudio.advancedfmk.util.Compressor

object DefaultFont {
    val DEFAULT_CJK = FreeTypeFont(
        Compressor.decode(
            DefaultFont.javaClass.getResourceAsStream("/assets/advancedfmk/fonts/star_rail.ttf.zstd")!!.readAllBytes()
        )
    )

    val ARABIC = FreeTypeFont(
        Compressor.decode(
            DefaultFont.javaClass.getResourceAsStream("/assets/advancedfmk/fonts/arabic.ttf.zstd")!!.readAllBytes()
        )
    )
}
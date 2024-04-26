package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.fontengine.ComposedFont
import net.fabricmc.api.ClientModInitializer

class AdvancedFrameworkFontEngineClient : ClientModInitializer {
    override fun onInitializeClient() {
        val font = ComposedFont()
        val glyphs = font.fetchGlyphs("测试! Hello World from UICompositor")

        for (i in glyphs.indices) {
            println("${"测试! Hello World from UICompositor".codePointAt(i).toChar()} ${glyphs[i].dimension}")
        }
    }
}
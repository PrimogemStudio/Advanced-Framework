package com.primogemstudio.advancedfmk.client

import net.fabricmc.api.ClientModInitializer
import org.lwjgl.util.freetype.FT_Color

class AdvancedFrameworkFontEngineClient: ClientModInitializer {
    override fun onInitializeClient() {
        println(FT_Color::class)
    }
}
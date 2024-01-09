package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.render.Shaders
import net.fabricmc.api.ClientModInitializer

class AdvancedFrameworkClient : ClientModInitializer {
    override fun onInitializeClient() {
        Shaders.init()
    }
}

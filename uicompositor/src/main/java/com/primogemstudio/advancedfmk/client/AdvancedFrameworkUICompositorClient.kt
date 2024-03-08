package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.render.uiframework.LuaVM
import net.fabricmc.api.ClientModInitializer

class AdvancedFrameworkUICompositorClient: ClientModInitializer {
    override fun onInitializeClient() = LuaVM.init()
}
package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.mmd.entity.Entities.register
import net.fabricmc.api.ModInitializer

class AdvancedFramework : ModInitializer {
    override fun onInitialize() {
        register()
    }

    companion object {
        const val MOD_ID = "advancedfmk"
    }
}

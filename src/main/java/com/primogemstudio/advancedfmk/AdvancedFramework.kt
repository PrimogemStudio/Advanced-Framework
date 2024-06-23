package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.mmd.entity.Entities
import net.fabricmc.api.ModInitializer

class AdvancedFramework : ModInitializer {
    override fun onInitialize() {
        Entities.register()
    }

    companion object {
        const val MOD_ID = "advancedfmk"
    }
}

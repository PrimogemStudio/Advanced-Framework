package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.mmd.entity.Entities
import com.primogemstudio.advancedfmk.network.TestEntityAddPacket
import com.primogemstudio.advancedfmk.network.UpdatePacket
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry

class AdvancedFramework : ModInitializer {
    override fun onInitialize() {
        PayloadTypeRegistry.playS2C().register(UpdatePacket.TYPE, UpdatePacket.CODEC)
        PayloadTypeRegistry.playS2C().register(TestEntityAddPacket.TYPE, TestEntityAddPacket.CODEC)
        Entities.register()
    }

    companion object {
        const val MOD_ID = "advancedfmk"
    }
}

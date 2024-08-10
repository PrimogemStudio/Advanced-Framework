package com.primogemstudio.advancedfmk

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.mmd.entity.Entities
import net.fabricmc.api.ModInitializer
import net.minecraft.client.renderer.GameRenderer
import org.joml.Matrix4f

class AdvancedFramework : ModInitializer {
    override fun onInitialize() {
        Entities.register()
    }

    companion object {
        const val MOD_ID = "advancedfmk"
    }
}

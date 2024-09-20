package com.primogemstudio.advancedfmk.flutter

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ShaderInstance


object Shaders {
    @JvmField
    val BLIT_NO_FLIP = ShaderInstance(
        Minecraft.getInstance().resourceManager, "blit_no_flip", DefaultVertexFormat.POSITION_COLOR
    )
}


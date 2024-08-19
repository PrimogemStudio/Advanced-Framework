package com.primogemstudio.advancedfmk.client

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ShaderInstance

object Shader {
    val LIVE2D_SHADER = ShaderInstance(Minecraft.getInstance().resourceManager, "live2d", DefaultVertexFormat.POSITION_TEX)
}
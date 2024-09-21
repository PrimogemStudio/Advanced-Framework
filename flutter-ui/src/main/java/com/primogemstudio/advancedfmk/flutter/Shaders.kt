package com.primogemstudio.advancedfmk.flutter

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.resources.ResourceLocation
import org.ladysnake.satin.api.managed.ShaderEffectManager


object Shaders {
    @JvmField
    val BLIT_NO_FLIP = ShaderInstance(
        Minecraft.getInstance().resourceManager, "blit_no_flip", DefaultVertexFormat.POSITION_COLOR
    )

    @JvmField
    val POST_BLUR = ShaderEffectManager.getInstance().manage(ResourceLocation.withDefaultNamespace("shaders/filter/gaussian_blur.json"))
}


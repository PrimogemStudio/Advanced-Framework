package com.primogemstudio.advancedfmk.render

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.primogemstudio.advancedfmk.mmd.renderer.CustomRenderType
import ladysnake.satin.api.managed.ManagedCoreShader
import ladysnake.satin.api.managed.ManagedShaderEffect
import ladysnake.satin.api.managed.ShaderEffectManager
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.resources.ResourceLocation


object Shaders {
    @JvmField
    val ROUNDED_RECT = ShaderInstance(
        Minecraft.getInstance().resourceManager, "rounded_rect",
        DefaultVertexFormat.POSITION_COLOR
    )
    @JvmField
    val ROUNDED_RECT_CLIP = ShaderInstance(
        Minecraft.getInstance().resourceManager, "rounded_rect_clip",
        DefaultVertexFormat.POSITION_COLOR
    )
    @JvmField
    val ROUNDED_RECT_TEX = ShaderInstance(
        Minecraft.getInstance().resourceManager, "rounded_rect_tex",
        DefaultVertexFormat.POSITION_COLOR_TEX
    )
    @JvmField
    val MMD_SHADER = ShaderInstance(
        Minecraft.getInstance().resourceManager, "mmd_entity",
        CustomRenderType.ENTITY
    )
    @JvmField
    val FAST_GAUSSIAN_BLUR: ManagedShaderEffect = ShaderEffectManager.getInstance()
        .manage(ResourceLocation("shaders/filter/fast_gaussian_blur.json"))
    @JvmField
    val GAUSSIAN_BLUR: ManagedShaderEffect = ShaderEffectManager.getInstance()
        .manage(ResourceLocation("shaders/filter/gaussian_blur.json"))
}


package com.primogemstudio.advancedfmk.render

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.primogemstudio.advancedfmk.AdvancedFramework
import ladysnake.satin.api.managed.ManagedCoreShader
import ladysnake.satin.api.managed.ManagedShaderEffect
import ladysnake.satin.api.managed.ShaderEffectManager
import net.minecraft.resources.ResourceLocation


object Shaders {
    @JvmField
    val ROUNDED_RECT: ManagedCoreShader = ShaderEffectManager.getInstance().manageCoreShader(
        ResourceLocation(AdvancedFramework.MOD_ID, "rounded_rect"),
        DefaultVertexFormat.POSITION_COLOR
    )
    @JvmField
    val FAST_GAUSSIAN_BLUR: ManagedShaderEffect = ShaderEffectManager.getInstance()
        .manage(ResourceLocation(AdvancedFramework.MOD_ID, "shaders/filter/fast_gaussian_blur.json"))
    @JvmField
    val GAUSSIAN_BLUR: ManagedShaderEffect = ShaderEffectManager.getInstance()
        .manage(ResourceLocation(AdvancedFramework.MOD_ID, "shaders/filter/gaussian_blur.json"))

    fun init() {}
}


package com.primogemstudio.advancedfmk.render

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vertex.VertexFormatElement
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.resources.ResourceLocation
import org.ladysnake.satin.api.managed.ManagedShaderEffect
import org.ladysnake.satin.api.managed.ShaderEffectManager


object Shaders {
    @JvmField
    val ROUNDED_RECT = ShaderInstance(
        Minecraft.getInstance().resourceManager, "rounded_rect", DefaultVertexFormat.POSITION_COLOR
    )

    @JvmField
    val ROUNDED_RECT_CLIP = ShaderInstance(
        Minecraft.getInstance().resourceManager, "rounded_rect_clip", DefaultVertexFormat.POSITION_COLOR
    )

    @JvmField
    val ROUNDED_RECT_TEX = ShaderInstance(
        Minecraft.getInstance().resourceManager, "rounded_rect_tex", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP
    )

    @JvmField
    val ROUNDED_RECT_TEX_CLIP = ShaderInstance(
        Minecraft.getInstance().resourceManager,
        "rounded_rect_tex_clip",
        DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP
    )

    @JvmField
    val FAST_GAUSSIAN_BLUR: ManagedShaderEffect = ShaderEffectManager.getInstance()
        .manage(ResourceLocation.withDefaultNamespace("shaders/filter/fast_gaussian_blur.json"))

    @JvmField
    val GAUSSIAN_BLUR: ManagedShaderEffect = ShaderEffectManager.getInstance()
        .manage(ResourceLocation.withDefaultNamespace("shaders/filter/gaussian_blur.json"))

    @JvmField
    val TEXTSWAP: ManagedShaderEffect =
        ShaderEffectManager.getInstance().manage(ResourceLocation.withDefaultNamespace("shaders/filter/textswap.json"))

    @JvmField
    val TEXTSWAP_CLIP: ManagedShaderEffect = ShaderEffectManager.getInstance()
        .manage(ResourceLocation.withDefaultNamespace("shaders/filter/textswap_clip.json"))

    @JvmField
    val POSITION_COLOR_TEX =
        VertexFormat.builder().add("Position", VertexFormatElement.POSITION).add("Color", VertexFormatElement.COLOR)
            .add("UV0", VertexFormatElement.UV0).build()
}


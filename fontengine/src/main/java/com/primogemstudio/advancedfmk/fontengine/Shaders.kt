package com.primogemstudio.advancedfmk.fontengine

import com.mojang.blaze3d.pipeline.TextureTarget
import net.minecraft.Util
import net.minecraft.Util.OS
import net.minecraft.resources.ResourceLocation
import org.ladysnake.satin.api.managed.ShaderEffectManager

object Shaders {
    val defaultClip = TextureTarget(1, 1, true, Util.getPlatform() == OS.OSX).apply {
        setClearColor(1f, 1f, 1f, 1f)
        clear(Util.getPlatform() == OS.OSX)
    }

    val TEXT_BLUR = ShaderEffectManager.getInstance()
        .manage(ResourceLocation.withDefaultNamespace("shaders/filter/text_gaussian_blur.json")).apply {
            setSamplerUniform("ClipSampler", defaultClip)
        }
}
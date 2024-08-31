package com.primogemstudio.advancedfmk.fontengine

import net.minecraft.resources.ResourceLocation
import org.ladysnake.satin.api.managed.ShaderEffectManager

object Shaders {
    val TEXT_BLUR = ShaderEffectManager.getInstance()
        .manage(ResourceLocation.withDefaultNamespace("shaders/filter/text_gaussian_blur.json"))
}
package com.primogemstudio.advancedfmk.fontengine

import ladysnake.satin.api.managed.ShaderEffectManager
import net.minecraft.resources.ResourceLocation

object Shaders {
    val TEXT_BLUR = ShaderEffectManager.getInstance().manage(ResourceLocation("shaders/filter/text_gaussian_blur.json"))
}
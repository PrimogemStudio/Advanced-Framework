package com.primogemstudio.advancedfmk.render

import com.primogemstudio.advancedfmk.AdvancedFramework
import com.primogemstudio.advancedfmk.render.filter.FilterType
import net.minecraft.resources.ResourceLocation

object FilterTypes {
    @JvmField
    val GAUSSIAN_BLUR = FilterType(ResourceLocation(AdvancedFramework.MOD_ID, "gaussian_blur"))
    @JvmField
    val FAST_GAUSSIAN_BLUR = FilterType(ResourceLocation(AdvancedFramework.MOD_ID, "fast_gaussian_blur"))
}

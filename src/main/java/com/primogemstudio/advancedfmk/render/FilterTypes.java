package com.primogemstudio.advancedfmk.render;

import com.primogemstudio.advancedfmk.render.filter.FilterType;
import net.minecraft.resources.ResourceLocation;

import static com.primogemstudio.advancedfmk.AdvancedFramework.MOD_ID;

public class FilterTypes {
    public static final FilterType GAUSSIAN_BLUR = new FilterType(new ResourceLocation(MOD_ID, "gaussian_blur"));
    public static final FilterType FAST_GAUSSIAN_BLUR = new FilterType(new ResourceLocation(MOD_ID, "fast_gaussian_blur"));
}
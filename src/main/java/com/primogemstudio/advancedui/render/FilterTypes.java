package com.primogemstudio.advancedui.render;

import com.primogemstudio.advancedui.render.filter.FilterType;
import net.minecraft.resources.ResourceLocation;

import static com.primogemstudio.advancedui.AdvancedUI.MOD_ID;

public class FilterTypes {
    public static final FilterType GAUSSIAN_BLUR = new FilterType(new ResourceLocation(MOD_ID, "gaussian_blur"));
    public static final FilterType FAST_GAUSSIAN_BLUR = new FilterType(new ResourceLocation(MOD_ID, "fast_gaussian_blur"));
}

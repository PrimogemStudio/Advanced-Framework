package com.primogemstudio.advancedui.render.filter;

import net.minecraft.resources.ResourceLocation;

import static com.primogemstudio.advancedui.AdvancedUI.MOD_ID;

public class FilterType {
    public static final FilterType GAUSSIAN_BLUR = new FilterType(new ResourceLocation(MOD_ID, "gaussian_blur"));
    private final ResourceLocation id;
    public FilterType(ResourceLocation location) {
        id = location;
    }

    public ResourceLocation getId() {
        return id;
    }
}

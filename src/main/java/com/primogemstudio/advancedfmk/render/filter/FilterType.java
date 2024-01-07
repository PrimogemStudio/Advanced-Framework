package com.primogemstudio.advancedfmk.render.filter;

import net.minecraft.resources.ResourceLocation;

public class FilterType {
    private final ResourceLocation id;

    public FilterType(ResourceLocation location) {
        id = location;
    }

    public ResourceLocation getId() {
        return id;
    }
}

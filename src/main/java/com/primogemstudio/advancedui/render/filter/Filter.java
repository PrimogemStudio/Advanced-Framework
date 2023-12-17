package com.primogemstudio.advancedui.render.filter;

import com.mojang.blaze3d.pipeline.RenderTarget;

import java.util.Map;

public interface Filter {
    RenderTarget getTarget();

    void setArgs(Map<String, Object> data);
    void render(float partialTicks);

    void enable();

    void reset();
}

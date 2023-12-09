package com.primogemstudio.advancedui.render.filter;

import com.mojang.blaze3d.pipeline.RenderTarget;

public interface Filter {
    RenderTarget getTarget();

    void render(float partialTicks);

    void enable();

    void reset();
}

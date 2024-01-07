package com.primogemstudio.advancedfmk.render.filter;

import com.mojang.blaze3d.pipeline.RenderTarget;

public interface Filter {
    RenderTarget getTarget();

    void setArg(String name, Object value);

    void render(float partialTicks);

    void enable();

    void reset();
}

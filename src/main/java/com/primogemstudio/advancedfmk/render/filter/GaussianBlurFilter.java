package com.primogemstudio.advancedfmk.render.filter;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;

import static com.primogemstudio.advancedfmk.render.Shaders.GAUSSIAN_BLUR;
import static net.minecraft.client.Minecraft.ON_OSX;

public class GaussianBlurFilter implements Filter {
    private static final TextureTarget target = new TextureTarget(1, 1, true, ON_OSX);
    private boolean enable;
    private int radius = 5;
    private boolean enableFrostedGrass = false;

    static {
        target.setClearColor(0, 0, 0, 0);
    }

    @Override
    public RenderTarget getTarget() {
        return target;
    }

    @Override
    public void setArg(String name, Object value) {
        switch (name) {
            case "Radius" -> radius = (int) value;
            case "EnableFrostGrass" -> enableFrostedGrass = (boolean) value;
        }
    }

    @Override
    public void render(float partialTicks) {
        if (enable) {
            GAUSSIAN_BLUR.setSamplerUniform("InputSampler", target);
            GAUSSIAN_BLUR.setUniformValue("Radius", (int) (Math.ceil((double) Math.max(4, radius) / 2) * 2));
            GAUSSIAN_BLUR.setUniformValue("DigType", enableFrostedGrass ? 1 : 0);
            GAUSSIAN_BLUR.render(partialTicks);
        }
    }

    @Override
    public void enable() {
        enable = true;
    }

    @Override
    public void reset() {
        enable = false;
        radius = 0;
    }
}

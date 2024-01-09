package com.primogemstudio.advancedfmk.render;

import com.primogemstudio.advancedfmk.render.filter.FastGaussianBlurFilter;
import com.primogemstudio.advancedfmk.render.filter.Filter;
import com.primogemstudio.advancedfmk.render.filter.FilterType;
import com.primogemstudio.advancedfmk.render.filter.GaussianBlurFilter;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;

import static com.primogemstudio.advancedfmk.render.FilterTypes.FAST_GAUSSIAN_BLUR;
import static com.primogemstudio.advancedfmk.render.FilterTypes.GAUSSIAN_BLUR;
import static net.minecraft.client.Minecraft.ON_OSX;

public class RenderQueue {
    private static final RenderResource renderResource = new RenderResource();
    private static final Map<FilterType, Filter> filters = new HashMap<>();

    static {
        register(GAUSSIAN_BLUR, new GaussianBlurFilter());
        register(FAST_GAUSSIAN_BLUR, new FastGaussianBlurFilter());
    }

    public static void init(int width, int height) {
        var flag = width != renderResource.screenWidth || height != renderResource.screenHeight;
        if (flag) {
            renderResource.screenWidth = width;
            renderResource.screenHeight = height;
        }
        for (var f : filters.values()) {
            if (flag) f.getTarget().resize(width, height, ON_OSX);
            f.getTarget().clear(ON_OSX);
            f.reset();
        }
    }

    public static void register(FilterType type, Filter filter) {
        filters.put(type, filter);
    }

    public static void post(float partialTicks) {
        for (var f : filters.values()) {
            f.render(partialTicks);
        }
    }

    public static void flush(float partialTicks) {
        post(partialTicks);
        for (var f : filters.values()) {
            f.getTarget().clear(ON_OSX);
            f.reset();
        }
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
    }

    public static void setFilterArg(FilterType type, String name, Object value) {
        var filter = filters.get(type);
        filter.setArg(name, value);
    }

    public static void draw(Renderable renderable) {
        renderable.render(renderResource);
    }

    public static void draw(Renderable renderable, FilterType type) {
        var filter = filters.get(type);
        var target = filter.getTarget();
        target.bindWrite(true);
        renderable.render(renderResource);
        target.unbindWrite();
        filter.enable();
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
    }
}
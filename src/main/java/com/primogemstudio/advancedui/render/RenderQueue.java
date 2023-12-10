package com.primogemstudio.advancedui.render;

import com.google.common.collect.ImmutableMap;
import com.primogemstudio.advancedui.render.filter.Filter;
import com.primogemstudio.advancedui.render.filter.FilterType;
import com.primogemstudio.advancedui.render.filter.GaussianBlurFilter;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.client.Minecraft.ON_OSX;

public class RenderQueue {
    private static final RenderResource renderResource = new RenderResource();
    private static final Map<FilterType, Filter> filters = new HashMap<>();

    static {
        register(FilterType.GAUSSIAN_BLUR, new GaussianBlurFilter());
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

    public static void register(FilterType type, Filter impl) {
        filters.put(type, impl);
    }

    public static void post(float partialTicks) {
        for (var f : filters.values()) {
            f.render(partialTicks);
        }
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
    }
}

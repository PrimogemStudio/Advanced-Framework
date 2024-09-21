package com.primogemstudio.advancedfmk.flutter;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Unique;

import java.lang.ref.Cleaner;

public class FlutterInstance implements AutoCloseable {
    private final Cleaner.Cleanable cleaner;
    public final long handle;
    public final Rect rect;
    public int width, height;
    public boolean pressed;

    public FlutterInstance(String assets, Rect rect, int width, int height) {
        handle = FlutterNative.createInstance(assets);
        this.rect = rect;
        this.width = width;
        this.height = height;
        sendSizeEvent();
        Events.register(this);
        var _handle = handle;
        cleaner = FlutterNative.cleaner.register(this, () -> FlutterNative.destroyInstance(_handle));
    }

    public boolean hitTest(double x, double y) {
        return x > rect.left && x < rect.right && y > rect.top && y < rect.bottom;
    }

    void sendSizeEvent() {
        FlutterNative.sendMetricsEvent(handle, rect.right - rect.left, rect.bottom - rect.top, 0);
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        ViewEvent.resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
    }

    public void pollEvents() {
        FlutterNative.pollEvents(handle);
    }

    public int getTexture() {
        return FlutterNative.getTexture(handle);
    }

    public void renderToScreen() {
        var x = rect.left;
        var y = Minecraft.getInstance().getWindow().getHeight() - rect.bottom;
        var width = rect.right - rect.left;
        var height = rect.bottom - rect.top;
        var w = Minecraft.getInstance().getWindow().getWidth();
        var h = Minecraft.getInstance().getWindow().getHeight();
        GlStateManager._colorMask(true, true, true, false);
        GlStateManager._disableDepthTest();
        GlStateManager._depthMask(false);
        GlStateManager._viewport(x, y, width, height);
        GlStateManager._enableBlend();
        var shader = Shaders.BLIT_NO_FLIP;
        shader.getUniform("PositionOffset").set((float) (x / w), (float) (y / h));
        shader.setSampler("DiffuseSampler", getTexture());
        shader.apply();
        BufferBuilder buff = RenderSystem.renderThreadTesselator().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLIT_SCREEN);
        buff.addVertex(0.0F, 0.0F, 0.0F);
        buff.addVertex(1.0F, 0.0F, 0.0F);
        buff.addVertex(1.0F, 1.0F, 0.0F);
        buff.addVertex(0.0F, 1.0F, 0.0F);
        BufferUploader.draw(buff.buildOrThrow());
        shader.clear();
        GlStateManager._disableBlend();
        GlStateManager._depthMask(true);
        GlStateManager._colorMask(true, true, true, true);
    }

    @Override
    public void close() {
        Events.unregister(this);
        cleaner.clean();
    }
}

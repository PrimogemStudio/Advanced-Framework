package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.primogemstudio.advancedfmk.flutter.FlutterInstance;
import com.primogemstudio.advancedfmk.flutter.Rect;
import com.primogemstudio.advancedfmk.flutter.Shaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {
    @Unique
    private static FlutterInstance instance;

    @Inject(method = "flipFrame", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSwapBuffers(J)V"), remap = false)
    private static void flipFrame(long l, CallbackInfo ci) {
        var window = Minecraft.getInstance().getWindow();
        if (instance == null) {
            instance = new FlutterInstance("/home/coder2/flutter/flutter_demo/build/linux/x64/release/bundle/data/flutter_assets", new Rect(0, window.getHeight() - 600 - 100, 800, window.getHeight() - 100), 800, 600);
        }
        instance.pollEvents();
        var rect = instance.rect;
        blit(rect.left, window.getHeight() - rect.bottom, rect.right - rect.left, rect.bottom - rect.top);
    }

    @Unique
    private static void blit(int x, int y, int width, int height) {
        var w = Minecraft.getInstance().getWindow().getWidth();
        var h = Minecraft.getInstance().getWindow().getHeight();
        GlStateManager._colorMask(true, true, true, false);
        GlStateManager._disableDepthTest();
        GlStateManager._depthMask(false);
        GlStateManager._viewport(x, y, width, height);
        GlStateManager._enableBlend();
        var shader = Shaders.BLIT_NO_FLIP;
        shader.getUniform("PositionOffset").set((float) (x / w), (float) (y / h));
        shader.setSampler("DiffuseSampler", instance.getTexture());
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
}

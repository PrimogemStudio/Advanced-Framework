package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.flutter.FlutterInstance;
import com.primogemstudio.advancedfmk.flutter.Rect;
import com.primogemstudio.advancedfmk.flutter.Shaders;
import net.minecraft.client.Minecraft;
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
        if (instance == null) {
            var window = Minecraft.getInstance().getWindow();
            instance = new FlutterInstance("f:/c++/glfw-flutter/app", new Rect(0, window.getHeight() - 600, 800, window.getHeight()), 800, 600);
        }
        instance.pollEvents();
        blit(800, 600);
    }

    @Unique
    private static void blit(int width, int height) {
        GlStateManager._colorMask(true, true, true, false);
        GlStateManager._disableDepthTest();
        GlStateManager._depthMask(false);
        GlStateManager._viewport(0, 0, width, height);
        var shader = Shaders.BLIT_NO_FLIP;
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

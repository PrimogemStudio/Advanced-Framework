package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.flutter.FlutterNative;
import com.primogemstudio.advancedfmk.kui.Shaders;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class GameRendererMixin {
    @Unique
    private static long flutterInstance = 0;

    @Inject(method = "render", at = @At(value = "RETURN"), remap = false)
    private void flipFrame(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (flutterInstance == 0) {
            flutterInstance = FlutterNative.createInstance("/home/coder2/flutter/glfw-flutter/app");
            FlutterNative.sendMetricsEvent(flutterInstance, 800, 600, 0);
        }
        FlutterNative.pollEvents(flutterInstance);
        blit(800, 600);
    }

    @Unique
    private static void blit(int width, int height) {
        GlStateManager._colorMask(true, true, true, false);
        GlStateManager._disableDepthTest();
        GlStateManager._depthMask(false);
        GlStateManager._viewport(0, 0, width, height);
        var shader = Shaders.BLIT_NO_FLIP;
        shader.setSampler("DiffuseSampler", FlutterNative.getTexture(flutterInstance));
        shader.apply();
        BufferBuilder buff = RenderSystem.renderThreadTesselator().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLIT_SCREEN);
        buff.addVertex(0.0F, 0.0F, 0.0F);
        buff.addVertex(1.0F, 0.0F, 0.0F);
        buff.addVertex(1.0F, 1.0F, 0.0F);
        buff.addVertex(0.0F, 1.0F, 0.0F);
        BufferUploader.draw(buff.buildOrThrow());
        shader.clear();
        GlStateManager._depthMask(true);
        GlStateManager._colorMask(true, true, true, true);
    }
}

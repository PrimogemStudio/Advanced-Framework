package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.primogemstudio.advancedfmk.flutter.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.nglfwGetProcAddress;

@Mixin(RenderSystem.class)
public class GameRendererMixin {
    @Unique
    private static long flutterInstance = 0;
    @Unique
    private static RenderTarget renderTarget;

    @Inject(method = "flipFrame", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSwapBuffers(J)V"), remap = false)
    private static void flipFrame(long l, CallbackInfo ci) {
        if (flutterInstance == 0) {
            renderTarget = new TextureTarget(800, 600, false, false);
            var config = new RendererConfig();
            config.makeCurrent = BoolCallback.create(i -> true);
            config.present = BoolCallback.create(i -> true);
            config.clearCurrent = BoolCallback.create(i -> true);
            config.fbo = UIntCallback.create(i -> renderTarget.frameBufferId);
            config.resolver = ProcResolver.create((i, name) -> nglfwGetProcAddress(name));
            flutterInstance = FlutterNative.createInstance("F:/c++/glfw-flutter/app", config);
            FlutterNative.setPixelRatio(flutterInstance, 1);
            FlutterNative.sendMetricsEvent(flutterInstance, 800, 600, 0);
        }
        renderTarget.bindWrite(true);
        FlutterNative.pollEvents(flutterInstance);
        renderTarget.unbindWrite();
        renderTarget.blitToScreen(400, 300);
    }
}

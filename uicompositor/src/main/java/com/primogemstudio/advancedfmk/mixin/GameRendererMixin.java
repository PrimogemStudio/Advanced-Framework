package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.flutter.BoolCallback;
import com.primogemstudio.advancedfmk.flutter.FlutterNative;
import com.primogemstudio.advancedfmk.flutter.RendererConfig;
import com.primogemstudio.advancedfmk.flutter.UIntCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Unique
    private static long flutterInstance = 0;

    @Inject(method = "render", at = @At("RETURN"))
    private void postRender(DeltaTracker deltaTracker, boolean bl, CallbackInfo ci) {
        if (flutterInstance == 0) {
            var window = Minecraft.getInstance().getWindow();
            var handle = window.getWindow();
            var config = new RendererConfig();
            config.makeCurrent = BoolCallback.create(i -> {
                GLFW.glfwMakeContextCurrent(handle);
                return true;
            });
            config.present = BoolCallback.create(i -> {
                GLFW.glfwSwapBuffers(handle);
                return true;
            });
            config.clearCurrent = BoolCallback.create(i -> true);
            config.fbo = UIntCallback.create(i -> 0);
            flutterInstance = FlutterNative.createInstance("F:/c++/glfw-flutter/app", config);
            int[] width = {0}, height = {0};
            GLFW.glfwGetFramebufferSize(handle, width, height);
            FlutterNative.setPixelRatio(flutterInstance, (double) width[0] / window.getWidth());
            FlutterNative.sendMetricsEvent(flutterInstance, window.getWidth(), window.getHeight(), 0);
        }
    }
}

package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.ScreenManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import com.primogemstudio.advancedfmk.flutter.FlutterNative;
import com.primogemstudio.advancedfmk.flutter.ViewEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.Functions.*;

@Mixin(Window.class)
public class FlutterWindowMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(WindowEventHandler eventHandler, ScreenManager screenManager, DisplayData displayData, String preferredFullscreenVideoMode, String title, CallbackInfo ci) {
        FlutterNative.init(GetKeyName, GetClipboardString, SetClipboardString, GetProcAddress);
    }

    @Inject(method = "onResize", at = @At("RETURN"))
    private void onResize(long window, int width, int height, CallbackInfo ci) {
        ViewEvent.resize(width, height);
    }
}
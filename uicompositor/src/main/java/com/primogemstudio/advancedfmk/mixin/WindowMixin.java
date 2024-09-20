package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.ScreenManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import com.primogemstudio.advancedfmk.flutter.FlutterNative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.Functions.*;

@Mixin(Window.class)
public class WindowMixin {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/ScreenManager;getMonitor(J)Lcom/mojang/blaze3d/platform/Monitor;"))
    private void init(WindowEventHandler eventHandler, ScreenManager screenManager, DisplayData displayData, String preferredFullscreenVideoMode, String title, CallbackInfo ci) {
        /*System.out.println("Test!");
        System.out.println(GetProcAddress);
        FlutterNative.init(GetKeyName, GetClipboardString, SetClipboardString, GetProcAddress);*/
    }
}
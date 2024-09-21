package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.flutter.FlutterKeyEvent;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "keyPress", at = @At("HEAD"))
    private void keyPress(long window, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (window != minecraft.getWindow().getWindow()) return;
        FlutterKeyEvent.INSTANCE.onKey(window, key, scanCode, action, modifiers);
    }
}

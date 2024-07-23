package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.platform.Window;
import com.primogemstudio.advancedfmk.kui.animation.EventLoop;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Final
    private Window window;

    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo ci) {
        EventLoop.INSTANCE.start();
    }

    @Inject(at = @At("HEAD"), method = "getFramerateLimit", cancellable = true)
    private void getFramerateLimit(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(this.window.getFramerateLimit());
    }
}

package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Final
    private Window window;

    @Inject(at = @At("HEAD"), method = "getFramerateLimit", cancellable = true)
    private void getFramerateLimit(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(this.window.getFramerateLimit());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;limitDisplayFPS(I)V"), method = "runTick")
    private void rdLimit(int i) {
    }
}

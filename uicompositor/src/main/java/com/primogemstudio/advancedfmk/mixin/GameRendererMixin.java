package com.primogemstudio.advancedfmk.mixin;

import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.primogemstudio.advancedfmk.kui.pipe.IndeterminateVarsKt.composeFrame;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Unique
    private static int width, height = 0;

    @Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/GuiGraphics;<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V"))
    private void preRender(DeltaTracker deltaTracker, boolean bl, CallbackInfo ci) {
        int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        if (width != w || height != h) {
            width = w;
            height = h;
            composeFrame().resize(w, h, Util.getPlatform() == Util.OS.OSX);
        }
    }
}

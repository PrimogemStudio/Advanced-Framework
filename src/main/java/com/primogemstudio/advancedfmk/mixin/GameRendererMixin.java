package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.render.uiframework.ui.GlobalVarsKt;
import glm_.vec2.Vec2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.ref.WeakReference;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/GuiGraphics;<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V"))
    private void preRender(float partialTicks, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        GlobalVarsKt.getData().setScreen_size(new Vec2(
                Minecraft.getInstance().getWindow().getGuiScaledWidth(),
                Minecraft.getInstance().getWindow().getGuiScaledHeight()
        ));
        GlobalVarsKt.getData().setTick(partialTicks);
    }
}

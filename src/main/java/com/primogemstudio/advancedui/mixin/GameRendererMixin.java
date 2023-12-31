package com.primogemstudio.advancedui.mixin;

import com.primogemstudio.advancedui.mmd.renderer.ShaderProvider;
import com.primogemstudio.advancedui.render.RenderQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/GuiGraphics;<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V"))
    private void preRender(float partialTicks, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        var window = Minecraft.getInstance().getWindow();
        RenderQueue.init(window.getWidth(), window.getHeight());
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        ShaderProvider.INSTANCE.Init();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void postRender(float partialTicks, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        RenderQueue.post(partialTicks);
    }
}

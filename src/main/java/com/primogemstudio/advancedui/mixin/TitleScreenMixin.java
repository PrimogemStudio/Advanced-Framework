package com.primogemstudio.advancedui.mixin;

import com.primogemstudio.advancedui.render.FilterTypes;
import com.primogemstudio.advancedui.render.RenderQueue;
import com.primogemstudio.advancedui.render.shape.RoundedRectangle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V", remap = false))
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        RenderQueue.draw(new RoundedRectangle(graphics.pose().last().pose(), 20, 0).resize(mouseX, mouseY, 200, 200).color(1, 1, 1, 0.4f), FilterTypes.FAST_GAUSSIAN_BLUR);
        RenderQueue.setFilterArg(FilterTypes.GAUSSIAN_BLUR, "Radius", 4);
        RenderQueue.flush(partialTick);
    }
}

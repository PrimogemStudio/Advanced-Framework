package com.primogemstudio.advancedui.mixin;

import com.primogemstudio.advancedui.render.RenderQueue;
import com.primogemstudio.advancedui.render.filter.FilterType;
import com.primogemstudio.advancedui.render.shape.RoundedRectangle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    @Inject(at = @At("HEAD"), method = "render")
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        var matrix = graphics.pose().last().pose();
        var center = new Vector2f(mouseX, mouseY);
        var size = new Vector2f(100, 100);
        var color = new Vector4f(1, 1, 1, 0.7f);
        RenderQueue.draw(new RoundedRectangle(matrix, center, size, color, 10, 0), FilterType.GAUSSIAN_BLUR);
    }
}

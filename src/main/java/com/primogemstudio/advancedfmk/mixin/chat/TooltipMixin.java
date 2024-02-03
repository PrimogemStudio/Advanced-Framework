package com.primogemstudio.advancedfmk.mixin.chat;

import com.primogemstudio.advancedfmk.render.FilterTypes;
import com.primogemstudio.advancedfmk.render.shape.AbstractBackdropableShape;
import com.primogemstudio.advancedfmk.render.shape.RoundedRectangle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TooltipRenderUtil.class)
public class TooltipMixin {
    @Unique
    private static AbstractBackdropableShape rect;
    @Inject(at = @At("HEAD"), method = "renderTooltipBackground", cancellable = true)
    private static void renderTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, CallbackInfo ci) {
        if (rect == null) rect = new RoundedRectangle(x - 3, y - 3, width + 6, height + 6, Component.empty())
                .color(0f, 0f, 0f, 0.2f)
                .thickness(0f)
                .smoothedge(0.001f)
                .setType(FilterTypes.GAUSSIAN_BLUR)
                .addData("Radius", 20)
                .addData("EnableFrostGrass", false);
        ((RoundedRectangle) rect).resize(x - 3, y - 3, width + 6, height + 6)
                .radius(Math.min(width, height) / 2f)
                .renderWidget(guiGraphics, 0, 0, 0f);
        ci.cancel();
    }
}

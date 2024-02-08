package com.primogemstudio.advancedfmk.mixin.chat;

import com.primogemstudio.advancedfmk.render.ColorComponent;
import com.primogemstudio.advancedfmk.render.ColorIntExtentionsKt;
import com.primogemstudio.advancedfmk.render.FilterTypes;
import com.primogemstudio.advancedfmk.render.shape.AbstractBackdropableShape;
import com.primogemstudio.advancedfmk.render.shape.RoundedRectangle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Unique
    private AbstractBackdropableShape rect;
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), method = "render")
    public void renderWidget(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int c) {
        if (rect == null) rect = new RoundedRectangle(minX, minY, maxX - minX, maxY - minY, Component.empty())
                .thickness(0f)
                .smoothedge(0.001f)
                .setType(FilterTypes.GAUSSIAN_BLUR)
                .addData("Radius", 20)
                .addData("EnableFrostGrass", false);
        ((RoundedRectangle) rect).resize(minX, minY, maxX - minX, maxY - minY)
                .color(
                        ColorIntExtentionsKt.colorFetchM(c, ColorComponent.R),
                        ColorIntExtentionsKt.colorFetchM(c, ColorComponent.G),
                        ColorIntExtentionsKt.colorFetchM(c, ColorComponent.B),
                        Math.max(1f - ColorIntExtentionsKt.colorFetchM(c, ColorComponent.A), 0.1f))
                .radius(Math.min(maxX - minX, maxY - minY) / 2f)
                .renderWidget(instance, 0, 0, 0f);
    }
}

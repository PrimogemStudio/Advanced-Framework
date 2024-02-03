package com.primogemstudio.advancedfmk.mixin.chat;

import com.primogemstudio.advancedfmk.render.FilterTypes;
import com.primogemstudio.advancedfmk.render.shape.AbstractBackdropableShape;
import com.primogemstudio.advancedfmk.render.shape.RoundedRectangle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static com.primogemstudio.advancedfmk.test.ImguiRender.*;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    private AbstractBackdropableShape rect;
    private GuiGraphics guiGraphics;
    private int mouseX, mouseY;
    private float partialTick;
    @Inject(at = @At("HEAD"), method = "render")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        this.guiGraphics = guiGraphics;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.partialTick = partialTick;
    }
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), method = "render")
    public void renderWidget(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int color) {
        var c = new Color(color);
        if (rect == null) rect = new RoundedRectangle(minX, minY, maxX - minX, maxY - minY, Component.empty())
                .color(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.1f)
                .thickness(0f)
                .smoothedge(0.001f)
                .setType(FilterTypes.GAUSSIAN_BLUR)
                .addData("Radius", 20)
                .addData("EnableFrostGrass", false);
        ((RoundedRectangle) rect).resize(minX, minY, maxX - minX, maxY - minY)
                .radius(Math.min(maxX - minX, maxY - minY) / 2f)
                .renderWidget(guiGraphics, mouseX, mouseY, partialTick);
    }
}

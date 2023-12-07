package com.primogemstudio.advancedui.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedui.AdvancedUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ButtonRendererMixin {
    @Inject(at = @At("RETURN"), method = "render")
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        RenderSystem.setShader(AdvancedUI.ROUNDED_RECT::getProgram);
        var x1 = mouseX;
        var x2 = x1 + 200;
        var y1 = mouseY;
        var y2 = y1 + 100;
        var color = 0x77333333;
        var matrix = graphics.pose().last().pose();
        var buff = Tesselator.getInstance().getBuilder();
        buff.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buff.vertex(matrix, x1, y1, 0).color(color).endVertex();
        buff.vertex(matrix, x1, y2, 0).color(color).endVertex();
        buff.vertex(matrix, x2, y2, 0).color(color).endVertex();
        buff.vertex(matrix, x2, y1, 0).color(color).endVertex();
        RenderSystem.enableBlend();
        BufferUploader.drawWithShader(buff.end());
        RenderSystem.disableBlend();
    }
}

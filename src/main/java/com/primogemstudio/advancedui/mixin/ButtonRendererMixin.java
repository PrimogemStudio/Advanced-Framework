package com.primogemstudio.advancedui.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedui.AdvancedUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class ButtonRendererMixin extends Screen {
    protected ButtonRendererMixin(Component title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "render")
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        RenderSystem.setShader(AdvancedUI.ROUNDED_RECT::getProgram);
        AdvancedUI.ROUNDED_RECT.findUniform2f("Resolution").set(width, height);
        AdvancedUI.ROUNDED_RECT.findUniform2f("Center").set(mouseX, mouseY);
        var x1 = mouseX - 100;
        var x2 = mouseX + 100;
        var y1 = mouseY - 50;
        var y2 = mouseY + 50;
        var matrix = graphics.pose().last().pose();
        var buff = Tesselator.getInstance().getBuilder();
        buff.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buff.vertex(matrix, x1, y1, 0).color(0.1f, 0.3f, 0.2f, 0.7f).endVertex();
        buff.vertex(matrix, x1, y2, 0).color(0.1f, 0.3f, 0.2f, 0.7f).endVertex();
        buff.vertex(matrix, x2, y2, 0).color(0.1f, 0.3f, 0.2f, 0.7f).endVertex();
        buff.vertex(matrix, x2, y1, 0).color(0.1f, 0.3f, 0.2f, 0.7f).endVertex();
        RenderSystem.enableBlend();
        BufferUploader.drawWithShader(buff.end());
        RenderSystem.disableBlend();
    }
}

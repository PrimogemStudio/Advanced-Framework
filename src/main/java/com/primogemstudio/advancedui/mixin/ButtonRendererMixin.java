package com.primogemstudio.advancedui.mixin;

import com.primogemstudio.advancedui.AdvancedUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ButtonRendererMixin {
    @Inject(at = @At("HEAD"), method = "render")
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        AdvancedUI.ROUNDED_RECT.render(partialTick);
    }
}

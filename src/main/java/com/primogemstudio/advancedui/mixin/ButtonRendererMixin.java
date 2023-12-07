package com.primogemstudio.advancedui.mixin;

import com.primogemstudio.advancedui.AdvancedUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractButton.class)
public abstract class ButtonRendererMixin {
    @Inject(at = @At("HEAD"), method = "renderWidget")
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        AdvancedUI.ROUNDED_RECT.render(partialTick);
    }
}

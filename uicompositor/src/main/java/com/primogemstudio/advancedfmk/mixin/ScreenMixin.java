package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.kui.pipe.ProgramEnvKt;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(at = @At("HEAD"), method = "render")
    public void onRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        ProgramEnvKt.setMouseX(mouseX);
        ProgramEnvKt.setMouseY(mouseY);
    }
}

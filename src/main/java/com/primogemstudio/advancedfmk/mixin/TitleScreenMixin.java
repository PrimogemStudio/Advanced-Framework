package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.UITestKt;
import com.primogemstudio.advancedfmk.render.uiframework.ui.GlobalVarsKt;
import com.primogemstudio.advancedfmk.render.uiframework.ui.UICompound;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Unique
    private final UICompound ui = UITestKt.loadNew();
    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        ui.render(GlobalVarsKt.getData(), guiGraphics);
    }
}

package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.render.uiframework.ui.GlobalVarsKt;
import com.primogemstudio.advancedfmk.render.uiframework.ui.UICompound;
import com.primogemstudio.advancedfmk.ui.UITestKt;
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
        long s = System.nanoTime();
        ui.render(GlobalVarsKt.getData(), guiGraphics);
        System.out.println((double) (System.nanoTime() - s) / 1000 / 1000);
    }
}

package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.UITestKt;
import com.primogemstudio.advancedfmk.render.uiframework.ui.GlobalVarsKt;
import com.primogemstudio.advancedfmk.render.uiframework.ui.UICompound;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Unique
    private UICompound ui;

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "init")
    public void init(CallbackInfo ci) {
        ui = UITestKt.loadNew();
    }
    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        ui.render(
                GlobalVarsKt.getData(),
                guiGraphics.pose().last().pose()
        );
    }
}

package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.render.kui.GlobalData;
import com.primogemstudio.advancedfmk.render.kui.KUITest;
import com.primogemstudio.advancedfmk.render.kui.elements.AbstractElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenKUIMixin {
    @Unique
    private static final KUITest test = new KUITest();

    @Unique
    private static int mx = 0;
    @Unique
    private static int my = 0;

    static {
        new Thread(() -> {
            while (true) {
                ((AbstractElement) test.getElem().getSubElements().getFirst()).getPos().set(mx - 50, my);
                ((AbstractElement) test.getElem().getSubElements().get(1)).getPos().set(mx, my);
            }
        }).start();
    }

    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        test.render(GlobalData.genData(graphics, partialTick));
        mx = mouseX;
        my = mouseY;
    }
}
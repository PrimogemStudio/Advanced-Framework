package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.kui.GlobalData;
import com.primogemstudio.advancedfmk.kui.KUITest;
import com.primogemstudio.advancedfmk.kui.elements.RealElement;
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
                test.getElem().subElement("test").getPos().set(mx - 50, my);
                test.getElem().subElement("testtext").getPos().set(mx, my);
            }
        }).start();
    }

    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        test.getElem().render(GlobalData.genData(graphics, partialTick));
        mx = mouseX;
        my = mouseY;
    }
}
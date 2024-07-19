package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.kui.GlobalData;
import com.primogemstudio.advancedfmk.kui.KUITest;
import com.primogemstudio.advancedfmk.kui.animation.DefaultFunctionsKt;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static java.lang.Math.min;

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
                long i = System.currentTimeMillis() % 1500;
                double prox = DefaultFunctionsKt.getBounceOut().gen(((double) min(i, 1000)) / 1000) * 100;
                test.getElem().subElement("test").getPos().set(mx - 50 + (int) prox, my);
                test.getElem().subElement("testtext").getPos().set(mx + (int) prox, my);
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
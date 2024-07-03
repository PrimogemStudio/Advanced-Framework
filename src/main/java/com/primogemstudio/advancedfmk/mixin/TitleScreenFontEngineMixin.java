package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.fontengine.BufferManager;
import com.primogemstudio.advancedfmk.fontengine.ComposedFont;
import com.primogemstudio.advancedfmk.render.kui.GlobalData;
import com.primogemstudio.advancedfmk.render.kui.KUITest;
import com.primogemstudio.advancedfmk.render.kui.elements.RealElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenFontEngineMixin {
    @Unique
    private static final ComposedFont font = new ComposedFont();
    @Unique
    private static final String TEXTT = "ࣻمرحبا بالعالم!";
    @Unique
    private static final String TEXTTT = "*** -> <- |>";
    @Unique
    private static final String TEXT = "测试! *** -> |> Hello world from UICompositor!";

    @Unique
    private static final KUITest test = new KUITest();
    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        BufferManager.INSTANCE.updateBufferColor(0x00ffffff);
        BufferManager.INSTANCE.renderText((vertexConsumer, poseStack) -> {
            // font.drawText(vertexConsumer, poseStack, TEXTT, 0, 48, 9, new Vector4f(1f, 1f, 1f, 1f), 240);
            // font.drawText(vertexConsumer, poseStack, TEXTTT, 0, 60, 9, new Vector4f(1f, 1f, 1f, 1f), 240);
            font.drawText(vertexConsumer, poseStack, TEXT, 0, 72, 9, new Vector4f(1f, 1f, 1f, 1f), 240);
            return null;
        }, graphics, partialTick);

        // long l = System.nanoTime();
        test.render(GlobalData.genData(graphics, partialTick));
        ((RealElement) test.getElem().getSubElements().getFirst()).getPos().set(mouseX, mouseY);
        // System.out.println((double) (System.nanoTime() - l) / 1000 / 1000);
    }
}
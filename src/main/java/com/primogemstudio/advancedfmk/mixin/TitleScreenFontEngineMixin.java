package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.fontengine.BufferManager;
import com.primogemstudio.advancedfmk.fontengine.ComposedFont;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
// -Dorg.lwjgl.glfw.libname=/usr/lib/libglfw.so
@Mixin(TitleScreen.class)
public class TitleScreenFontEngineMixin {
    @Unique
    private static final ComposedFont font = new ComposedFont();

    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        BufferManager.INSTANCE.updateBufferColor(0xffffffff);
        BufferManager.INSTANCE.renderText((vertexConsumer, poseStack) -> {
            font.drawWrapText(vertexConsumer, poseStack, "abcdABCD!@#$%^&*()_+", 200, 200, 9, 500, new Vector4f(1f, 1f, 1f, 1f));
            return null;
        }, graphics, partialTick);
    }
}

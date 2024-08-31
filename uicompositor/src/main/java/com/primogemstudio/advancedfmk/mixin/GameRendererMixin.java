package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.kui.pipe.ProgramEnvKt;
import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.primogemstudio.advancedfmk.kui.pipe.IndeterminateVarsKt.clipFrame;
import static com.primogemstudio.advancedfmk.kui.pipe.IndeterminateVarsKt.composeFrame;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow @Final private Minecraft minecraft;
    @Unique
    private static int width, height = 0;

    @Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/GuiGraphics;<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V"))
    private void preRender(DeltaTracker deltaTracker, boolean bl, CallbackInfo ci) {
        int w = Minecraft.getInstance().getWindow().getWidth();
        int h = Minecraft.getInstance().getWindow().getHeight();

        if (width != w || height != h) {
            width = w;
            height = h;
            composeFrame().resize(w, h, Util.getPlatform() == Util.OS.OSX);
            clipFrame().resize(w, h, Util.getPlatform() == Util.OS.OSX);
        }


        int x = (int) (
                this.minecraft.mouseHandler.xpos() * (double) this.minecraft.getWindow().getGuiScaledWidth() / (double) this.minecraft.getWindow().getScreenWidth()
        );
        int y = (int) (
                this.minecraft.mouseHandler.ypos() * (double) this.minecraft.getWindow().getGuiScaledHeight() / (double) this.minecraft.getWindow().getScreenHeight()
        );

        ProgramEnvKt.setMouseX(x);
        ProgramEnvKt.setMouseY(y);
        ProgramEnvKt.setGuiScaledWidth(this.minecraft.getWindow().getGuiScaledWidth());
        ProgramEnvKt.setGuiScaledHeight(this.minecraft.getWindow().getGuiScaledHeight());
    }
}

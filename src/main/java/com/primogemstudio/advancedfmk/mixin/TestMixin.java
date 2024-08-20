package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.live2d.Live2DModel;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class TestMixin {
    @Unique
    private static final Live2DModel model = new Live2DModel("草神", "C:/Users/Hacker/Downloads/nahida/nahida/草神/");

    @Inject(at = @At("RETURN"), method = "render")
    public void render(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        model.update(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
    }
}

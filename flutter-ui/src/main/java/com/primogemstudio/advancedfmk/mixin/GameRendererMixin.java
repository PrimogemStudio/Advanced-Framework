package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.flutter.FlutterInstance;
import com.primogemstudio.advancedfmk.flutter.FlutterRect;
import com.primogemstudio.advancedfmk.flutter.FlutterViewEvent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Unique
    private static FlutterInstance instance;

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void flipFrame(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        if (instance == null) {
            var window = Minecraft.getInstance().getWindow();
            instance = new FlutterInstance("jar://app", new FlutterRect(), 600, 800);
            FlutterViewEvent.INSTANCE.resize(window.getWidth(), window.getHeight());
            instance.getComposeData().setBlurType(1);
            instance.getComposeData().setBlurRadius(8);
            instance.getComposeData().setNoise(0.005f);
            instance.getComposeData().setOpacity(0.8f);
        }
        instance.pollEvents();
        instance.renderToScreen();
    }
}

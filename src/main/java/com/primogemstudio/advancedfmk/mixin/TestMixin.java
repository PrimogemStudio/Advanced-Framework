package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.live2d.Live2DModel;
import com.primogemstudio.advancedfmk.live2d.MotionPriority;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(GameRenderer.class)
public class TestMixin {
    @Unique
    private static final Live2DModel model = new Live2DModel("JingYuan", "/home/coder2/LpkUnpacker/Test/character/");
    @Unique
    private static final Random random = new Random();
    @Unique
    private static boolean init = false;

    @Inject(at = @At("RETURN"), method = "render")
    public void render(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        if (!init) {
            // var c = model.getMotionCount(Live2DModel.MotionGroupIdle);
            // var es = model.getExpressions();
            // model.startMotion(Live2DModel.MotionGroupIdle, random.nextInt(c), MotionPriority.FORCE);
            // model.setExpression(es[random.nextInt(es.length)]);
            init = true;
        }
        model.update(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
    }
}

package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.live2d.Live2DModel;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GLFW.class, remap = false)
public class GLFWMixin {
    @Inject(at = @At("RETURN"), method = "glfwInit", remap = false)
    private static void _initGlfw(CallbackInfoReturnable<Boolean> cir) {
        Live2DModel.setGlfwGetTimeHandle(GLFW.Functions.GetTime);
    }
}

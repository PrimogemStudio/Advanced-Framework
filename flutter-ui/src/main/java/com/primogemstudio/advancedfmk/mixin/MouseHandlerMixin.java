package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.flutter.MouseEvent;
import com.primogemstudio.advancedfmk.flutter.PointerPhase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.*;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onPress", at = @At("HEAD"))
    private void onMouseButton(long window, int button, int action, int modifiers, CallbackInfo ci) {
        if (window != Minecraft.getInstance().getWindow().getWindow()) return;
        double[] x = {0};
        double[] y = {0};
        glfwGetCursorPos(window, x, y);
        if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS) {
            MouseEvent.onMouseButton(PointerPhase.kDown, x[0], y[0]);
        }
        if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE) {
            MouseEvent.onMouseButton(PointerPhase.kUp, x[0], y[0]);
        }
    }
}

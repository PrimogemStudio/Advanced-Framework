package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.flutter.FlutterMouseEvent;
import com.primogemstudio.advancedfmk.flutter.FlutterPointerPhase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.*;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int modifiers, CallbackInfo ci) {
        if (window != minecraft.getWindow().getWindow()) return;
        double[] x = {0};
        double[] y = {0};
        glfwGetCursorPos(window, x, y);
        if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS) {
            if (FlutterMouseEvent.INSTANCE.onMouseButton(FlutterPointerPhase.kDown, x[0], y[0])) ci.cancel();
        }
        if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE) {
            if (FlutterMouseEvent.INSTANCE.onMouseButton(FlutterPointerPhase.kUp, x[0], y[0])) ci.cancel();
        }
    }

    @Inject(method = "onMove", at = @At("HEAD"), cancellable = true)
    private void onMouseMove(long window, double x, double y, CallbackInfo ci) {
        if (window != minecraft.getWindow().getWindow()) return;
        if (FlutterMouseEvent.INSTANCE.onMouseMove(x, y)) ci.cancel();
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double xOffset, double yOffset, CallbackInfo ci) {
        if (window != minecraft.getWindow().getWindow()) return;
        double[] x = {0};
        double[] y = {0};
        glfwGetCursorPos(window, x, y);
        if (FlutterMouseEvent.INSTANCE.onMouseScroll(x[0], y[0], xOffset, yOffset)) ci.cancel();
    }
}

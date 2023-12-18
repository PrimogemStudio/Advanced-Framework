package com.primogemstudio.advancedui.mixin;

import com.primogemstudio.advancedui.test.ImguiRender;
import gln.cap.Caps;
import imgui.ImGui;
import imgui.ImguiKt;
import imgui.MouseButton;
import imgui.classes.Context;
import imgui.impl.gl.ImplGL3;
import imgui.impl.glfw.ImplGlfw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uno.gl.GlWindow;
import uno.glfw.GlfwWindow;

import java.util.Objects;

@Mixin(value = TitleScreen.class, priority = Integer.MAX_VALUE)
public class ImguiHook extends Screen {
    @Unique
    private static final ImGui imgui = ImGui.INSTANCE;
    @Unique
    private static final ImplGL3 implGl3;
    @Unique
    private static final ImplGlfw implGlfw;

    static {
        ImguiKt.MINECRAFT_BEHAVIORS = true;
        GlfwWindow glfwWindow = new GlfwWindow(Minecraft.getInstance().getWindow().getWindow());
        GlWindow window = new GlWindow(glfwWindow, Caps.Profile.COMPATIBILITY, true);
        window.makeCurrent(true);
        new Context();
        implGlfw = new ImplGlfw(window, false, null);
        implGl3 = new ImplGL3();
    }

    protected ImguiHook(Component title) {
        super(title);
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        implGl3.newFrame();
        implGlfw.newFrame();
        imgui.newFrame();
        ImguiRender.render(imgui);
        imgui.render();
        implGl3.renderDrawData(Objects.requireNonNull(imgui.getDrawData()));
    }

    @Override
    public boolean mouseScrolled(double d, double e, double amount) {
        if (imgui.getIo().getWantCaptureMouse()) {
            imgui.getIo().setMouseWheel((float) amount);
        }
        return super.mouseScrolled(d, e, amount);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void click(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        imgui.getIo().addMouseButtonEvent(MouseButton.Left, true);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        imgui.getIo().addMouseButtonEvent(MouseButton.Left, false);
        return super.mouseReleased(mouseX, mouseY, button);
    }
}

package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.primogemstudio.advancedfmk.AdvancedFramework;
import com.primogemstudio.advancedfmk.render.FilterTypes;
import com.primogemstudio.advancedfmk.render.Shaders;
import com.primogemstudio.advancedfmk.render.shape.RoundedRectangle;
import net.fabricmc.fabric.impl.client.rendering.FabricShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.primogemstudio.advancedfmk.test.ImguiRender.*;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow protected abstract <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget);
    @Unique
    private final RoundedRectangle rect = new RoundedRectangle(Pos[0], Pos[1], Size[0], Size[1], Component.empty()).color(Color).smoothedge(SmoothEdge).radius(Radius).thickness(Thickness);
    @Unique
    private final Map<Screen, Boolean> added = new HashMap<>();
    @Inject(method = "removed", at = @At(value = "RETURN"))
    public void removed(CallbackInfo ci) {
        added.remove((Screen) (Object) this);
    }
    @Inject(method = "render", at = @At(value = "RETURN"))
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        var scr = (Screen) (Object) this;
        if (!added.containsKey(scr) || !added.get(scr)) {
            added.put(scr, true);
            addRenderableWidget(rect);
        }
        rect.resize(Pos[0], Pos[1], Size[0], Size[1])
                .color(Color)
                .smoothedge(SmoothEdge)
                .radius(Radius)
                .thickness(Thickness)
                .setType(FastBlur ? FilterTypes.FAST_GAUSSIAN_BLUR : FilterTypes.GAUSSIAN_BLUR)
                .addData("Radius", BlurRadius)
                .addData("EnableFrostGrass", FrostGrass);
    }
}

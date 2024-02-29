package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.UITestKt;
import com.primogemstudio.advancedfmk.render.Shaders;
import com.primogemstudio.advancedfmk.render.uiframework.ui.GlobalVars;
import com.primogemstudio.advancedfmk.render.uiframework.ui.UICompound;
import glm_.vec2.Vec2;
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

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Unique
    private UICompound ui;

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "init")
    public void init(CallbackInfo ci) {
        ui = UITestKt.load();
    }
    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        var a = new TextureTarget(width, height, true, Minecraft.ON_OSX);
        a.setClearColor(0, 0, 0, 0);
        a.clear(Minecraft.ON_OSX);
        a.bindWrite(true);
        ui.component1().values().stream().toList().get(0).render(
                new GlobalVars(new Vec2(width, height)),
                guiGraphics.pose().last().pose()
        );


        var b = new TextureTarget(width, height, true, Minecraft.ON_OSX);
        b.setClearColor(0, 0, 0, 0f);
        b.clear(Minecraft.ON_OSX);
        b.bindWrite(true);
        var temp = ui.component1().values().stream().toList().get(0).getLocation().get("x");
        ui.component1().values().stream().toList().get(0).getLocation().put("x", bb -> 35f);
        ui.component1().values().stream().toList().get(0).setDisableAlpha(true);
        ui.component1().values().stream().toList().get(0).render(
                new GlobalVars(new Vec2(width, height)),
                guiGraphics.pose().last().pose()
        );
        ui.component1().values().stream().toList().get(0).getLocation().put("x", temp);
        ui.component1().values().stream().toList().get(0).setDisableAlpha(false);

        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        Shaders.GAUSSIAN_BLUR_CLIP.setSamplerUniform("InputSampler", a);
        Shaders.GAUSSIAN_BLUR_CLIP.setSamplerUniform("ClipSampler", b);
        Shaders.GAUSSIAN_BLUR_CLIP.setUniformValue("DigType", 0);
        Shaders.GAUSSIAN_BLUR_CLIP.setUniformValue("Radius", 16);
        Shaders.GAUSSIAN_BLUR_CLIP.render(partialTick);
    }
}

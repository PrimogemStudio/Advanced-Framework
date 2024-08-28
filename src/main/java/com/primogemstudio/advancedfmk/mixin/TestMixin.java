package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.live2d.Live2DModel;
import com.primogemstudio.advancedfmk.live2d.RendererKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(TitleScreen.class)
public class TestMixin {
    @Unique
    private static final Live2DModel model = new Live2DModel("JingYuan", "/home/coder2/LpkUnpacker/Test/character/");
    @Unique
    private static final Random random = new Random();
    @Unique
    private static boolean init = false;

    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        RendererKt.getTarget().resize(800, 800, false);
        RendererKt.getTarget().clear(false);
        if (!init) {
            // var c = model.getMotionCount(Live2DModel.MotionGroupIdle);
            // var es = model.getExpressions();
            // model.startMotion(Live2DModel.MotionGroupIdle, random.nextInt(c), MotionPriority.FORCE);
            // model.setExpression(es[random.nextInt(es.length)]);
            init = true;
        }
        RendererKt.getTarget().bindWrite(true);
        model.update(800, 800);
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

        var buff = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        RenderSystem.setShaderTexture(0, RendererKt.getTarget().getColorTextureId());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        buff.addVertex(100f, 100f, 1f).setUv(0f, 1f);
        buff.addVertex(100f, 200f, 1f).setUv(0f, 0f);
        buff.addVertex(200f, 200f, 1f).setUv(1f, 0f);
        buff.addVertex(200f, 100f, 1f).setUv(1f, 1f);
        RenderSystem.disableCull();
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
        BufferUploader.drawWithShader(buff.buildOrThrow());
    }
}

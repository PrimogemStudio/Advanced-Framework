package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.primogemstudio.advancedfmk.mmd.PMXModel;
import com.primogemstudio.advancedfmk.mmd.renderer.EntityRenderWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(TitleScreen.class)
public class GameRendererAdditionMixin {
    @Unique
    private EntityRenderWrapper wrapper;
    @Unique
    private static final MultiBufferSource.BufferSource source = MultiBufferSource.immediate(new ByteBufferBuilder(0x200000));

    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (wrapper == null) {
            wrapper = new EntityRenderWrapper(new PMXModel(new File("/home/coder2/mmd/lumine/lumine.pmx")));
            wrapper.getModel().animation.add(new File("/home/coder2/mmd/actions/custom_1.vmd"));
            wrapper.getModel().animation.setupAnimation();
        }
        var m = new Matrix4f().perspective(30 * 0.01745329238474369f, Minecraft.getInstance().getWindow().getWidth() / (float) Minecraft.getInstance().getWindow().getHeight(), 1f, -1f);
        m.translate(0f, 0f, 10998f);

        var pm = RenderSystem.getProjectionMatrix();
        var vs = RenderSystem.getVertexSorting();
        RenderSystem.setProjectionMatrix(m, VertexSorting.DISTANCE_TO_ORIGIN);
        var ps = new PoseStack();
        ps.scale(0.5f, 0.5f, 0.5f);
        ps.translate(0f, -1.45f, 0f);
        wrapper.render(0f, ps, source, 0xFF);
        source.endBatch();
        RenderSystem.setProjectionMatrix(pm, vs);
    }
}

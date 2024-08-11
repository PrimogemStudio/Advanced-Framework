package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.primogemstudio.advancedfmk.kui.GlobalData;
import com.primogemstudio.advancedfmk.kui.KUITestKt;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Mixin(TitleScreen.class)
public class TitleScreenKUIMixin {
    @Unique
    private EntityRenderWrapper wrapper;
    @Unique
    private static final MultiBufferSource.BufferSource source = MultiBufferSource.immediate(new ByteBufferBuilder(0x200000));

    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        KUITestKt.getInstance().getElem().render(GlobalData.genData(graphics, partialTick));
        if (wrapper == null) {
            wrapper = new EntityRenderWrapper(new PMXModel(new File("D:\\360极速浏览器X下载\\【女主角_荧】_by_原神_44aee89b335a6bcb7f0183dbfdeab3e5\\lumine.pmx")));
            wrapper.getModel().animation.add(new File("d:/1.vmd"));
            wrapper.getModel().animation.setupAnimation();
        }
        var m = new Matrix4f().perspective(30 * 0.01745329238474369f, Minecraft.getInstance().getWindow().getWidth() / (float) Minecraft.getInstance().getWindow().getHeight(), 1f, -1f);
        m.translate(0f, 0f, 10998f);
        RenderSystem.setProjectionMatrix(m, VertexSorting.DISTANCE_TO_ORIGIN);
        var ps = new PoseStack();
        ps.scale(0.5f, 0.5f, 0.5f);
        ps.translate(0f, -1.45f, 0f);
        wrapper.render(0f, ps, source, 0xFF);
        source.endBatch();
    }

    @Mixin(Minecraft.class)
    public static class MinecraftMixin {
        @Inject(at = @At("HEAD"), method = "reloadResourcePacks(ZLnet/minecraft/client/Minecraft$GameLoadCookie;)Ljava/util/concurrent/CompletableFuture;")
        public void onReload(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
            KUITestKt.getInstance().reload();
        }
    }
}
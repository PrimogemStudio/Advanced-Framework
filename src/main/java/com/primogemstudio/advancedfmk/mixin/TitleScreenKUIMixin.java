package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.primogemstudio.advancedfmk.kui.KUITestKt;
import com.primogemstudio.advancedfmk.live2d.Live2DModel;
import com.primogemstudio.advancedfmk.mmd.renderer.EntityRenderWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(TitleScreen.class)
public class TitleScreenKUIMixin {
    @Unique
    private EntityRenderWrapper wrapper;
    @Unique
    private static final MultiBufferSource.BufferSource source = MultiBufferSource.immediate(new ByteBufferBuilder(0x200000));
    @Unique
    private static final Live2DModel model = new Live2DModel("Hiyori", "/home/coder2/live2d-demo/res/Hiyori/");
    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        // KUITestKt.getInstance().getElem().render(GlobalData.genData(graphics, partialTick));

        model.registerTextures();
        model.update();
        model.getDrawableOrders().forEach(idx -> {
            if (idx != 78 && idx != 77) {
                var vertices = model.getDrawableVertices(idx);
                var indices = model.getDrawableVertexIndices(idx);

                if (!indices.isEmpty()) {
                    var buff = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_TEX);
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, model.registeredTextures.get(model.getDrawableTextureIndex(idx)));
                    indices.stream().map(vertices::get).forEach(vi -> buff.addVertex(vi.x * 200 + 100, (1 - vi.y) * 200, 0).setUv(vi.z, vi.w));

                    RenderSystem.disableBlend();
                    RenderSystem.disableCull();
                    BufferUploader.drawWithShader(buff.buildOrThrow());
                    RenderSystem.enableCull();
                    RenderSystem.enableBlend();
                }
            }
        });

        /*model.registerTextures();
        model.update();
        int s = model.registeredTextures.size();
        for (int idx = 0; idx < s; idx++) {
            var buff = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_TEX);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, model.registeredTextures.get(idx));
            var vertices = model.getVertices(idx);
            var indices = model.getVertexIndices(idx);

            indices.forEach(i -> buff.addVertex(vertices.get(i * 5) * 200 + 20, (1 - vertices.get(i * 5 + 1)) * 200 + 20, vertices.get(i * 5 + 2) * 10000).setUv(vertices.get(i * 5 + 3), vertices.get(i * 5 + 4)));

            RenderSystem.disableBlend();
            RenderSystem.disableCull();
            BufferUploader.drawWithShader(buff.buildOrThrow());
            RenderSystem.enableCull();
            RenderSystem.enableBlend();
        }*/

        /*if (wrapper == null) {
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
        RenderSystem.setProjectionMatrix(pm, vs);*/
    }

    @Mixin(Minecraft.class)
    public static class MinecraftMixin {
        @Inject(at = @At("HEAD"), method = "reloadResourcePacks(ZLnet/minecraft/client/Minecraft$GameLoadCookie;)Ljava/util/concurrent/CompletableFuture;")
        public void onReload(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
            KUITestKt.getInstance().reload();
        }
    }
}

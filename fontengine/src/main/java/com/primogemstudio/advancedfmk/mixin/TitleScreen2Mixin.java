package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeGlyph;
import com.primogemstudio.advancedfmk.ftwrap.vtxf.VertexFontInputStream;
import com.primogemstudio.advancedfmk.util.Compressor;
import kotlin.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(OptionsScreen.class)
public class TitleScreen2Mixin {
    @Unique
    private static final Map<Character, FreeTypeGlyph> fontProcessed;

    static {
        try (VertexFontInputStream i = new VertexFontInputStream(Compressor.INSTANCE.decode(Files.newInputStream(Path.of("../StarRailFont.vtxf"))))) {
            fontProcessed = i.parse().stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        var glyph = fontProcessed.get('测');
        var tess = Tesselator.getInstance().getBuilder();

        var stk = guiGraphics.pose();
        stk.pushPose();
        tess.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
        glyph.getIndices().forEach(integer -> {
            var f = glyph.getVertices().get(integer);
            tess.vertex((f.x * 100 * glyph.getWhscale() + 100), (f.y * 100 + 100), 0).color(255, 0, 255, 125).endVertex();
        });
        RenderSystem.enableBlend();
        BufferUploader.drawWithShader(tess.end());
        RenderSystem.disableBlend();

        stk.popPose();
    }
}

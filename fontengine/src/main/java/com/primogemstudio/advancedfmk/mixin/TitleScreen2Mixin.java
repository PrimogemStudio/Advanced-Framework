package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeGlyph;
import com.primogemstudio.advancedfmk.ftwrap.Shaders;
import com.primogemstudio.advancedfmk.ftwrap.vtxf.VertexFontInputStream;
import com.primogemstudio.advancedfmk.util.Compressor;
import kotlin.Pair;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
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

@Mixin(TitleScreen.class)
public class TitleScreen2Mixin {
    @Unique
    private static final Map<Character, FreeTypeGlyph> fontProcessed;
    @Unique
    private static final TextureTarget fontInternal = new TextureTarget(1, 1, true, Util.getPlatform() == Util.OS.OSX);

    static {
        try (VertexFontInputStream i = new VertexFontInputStream(Compressor.INSTANCE.decode(Files.newInputStream(Path.of("../StarRailFont.vtxf"))))) {
            fontProcessed = i.parse().stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        fontInternal.setClearColor(0f, 0f, 0f, 0f);
    }

    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        fontInternal.clear(Util.getPlatform() == Util.OS.OSX);
        fontInternal.resize(
                Minecraft.getInstance().getWindow().getWidth(),
                Minecraft.getInstance().getWindow().getHeight(),
                Util.getPlatform() == Util.OS.OSX
        );
        fontInternal.bindWrite(true);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        var glyph = fontProcessed.get('测');
        var tess = Tesselator.getInstance().getBuilder();

        var s = (float) Minecraft.getInstance().getWindow().getGuiScale();
        var stk = guiGraphics.pose();
        stk.pushPose();
        stk.scale(20 * glyph.getWhscale() / s, 20 / s, 0);
        stk.translate(1.5, 1.5, 0);

        tess.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
        glyph.getIndices().forEach(integer -> {
            var f = glyph.getVertices().get(integer);
            tess.vertex(stk.last().pose(), f.x, f.y, 0).color(255, 255, 255, 255).endVertex();
        });
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        BufferUploader.drawWithShader(tess.end());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();

        Shaders.INSTANCE.getTEXT_BLUR().setSamplerUniform("BaseLayer", fontInternal);
        Shaders.INSTANCE.getTEXT_BLUR().render(partialTick);

        stk.popPose();
    }
}

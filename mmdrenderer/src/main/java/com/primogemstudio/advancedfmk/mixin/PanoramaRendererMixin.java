package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.CubeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CubeMap.class)
public class PanoramaRendererMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;addVertex(FFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"), method = "render")
    public VertexConsumer reject(BufferBuilder instance, float x, float y, float z) {
        instance.addVertex(x * 5f, y * 5f, z * 5f);
        return instance;
    }
}

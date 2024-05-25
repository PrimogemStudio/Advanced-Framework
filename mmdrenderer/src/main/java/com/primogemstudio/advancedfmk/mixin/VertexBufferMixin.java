package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.interfaces.RenderedBufferExt;
import com.primogemstudio.advancedfmk.mmd.PMXModel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.ByteBuffer;

@Mixin(VertexBuffer.class)
public abstract class VertexBufferMixin {
    @Shadow
    @Nullable
    protected abstract RenderSystem.AutoStorageIndexBuffer uploadIndexBuffer(BufferBuilder.DrawState drawState, @Nullable ByteBuffer buffer);

    @Unique
    private PMXModel getModel(BufferBuilder.RenderedBuffer buffer) {
        return ((RenderedBufferExt) buffer).getPMXModel();
    }

    @Redirect(method = "upload", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder$DrawState;indexCount()I"))
    private int indexCount(BufferBuilder.DrawState instance, BufferBuilder.RenderedBuffer buffer) {
        var model = getModel(buffer);
        return model != null ? model.indexCount : instance.indexCount();
    }

    @Redirect(method = "upload", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder$DrawState;indexType()Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;"))
    private VertexFormat.IndexType indexType(BufferBuilder.DrawState instance, BufferBuilder.RenderedBuffer buffer) {
        var model = getModel(buffer);
        return model != null ? VertexFormat.IndexType.INT : instance.indexType();
    }

    @Redirect(method = "upload", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexBuffer;uploadIndexBuffer(Lcom/mojang/blaze3d/vertex/BufferBuilder$DrawState;Ljava/nio/ByteBuffer;)Lcom/mojang/blaze3d/systems/RenderSystem$AutoStorageIndexBuffer;"))
    private RenderSystem.AutoStorageIndexBuffer uploadIndexBuffer(VertexBuffer instance, BufferBuilder.DrawState drawState, ByteBuffer buffer, BufferBuilder.RenderedBuffer buff) {
        var model = getModel(buff);
        if (model != null) {
            model.bindIndices();
            return null;
        }
        return uploadIndexBuffer(drawState, buffer);
    }
}

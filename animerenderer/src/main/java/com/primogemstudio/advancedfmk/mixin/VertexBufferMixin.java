package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.interfaces.DrawStateExt;
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
    protected abstract RenderSystem.AutoStorageIndexBuffer uploadIndexBuffer(MeshData.DrawState drawState, @Nullable ByteBuffer byteBuffer);

    @Unique
    private PMXModel model;

    @Unique
    private PMXModel getModel(MeshData.DrawState state) {
        return ((DrawStateExt) (Object) state).getPMXModel();
    }

    /*@Redirect(method = "upload", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/MeshData$DrawState;indexCount()I"))
    private int indexCount(MeshData.DrawState state) {
        var model = getModel(state);
        return model != null ? model.indexCount : state.indexCount();
    }

    @Redirect(method = "upload", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/MeshData$DrawState;indexType()Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;"))
    private VertexFormat.IndexType indexType(MeshData.DrawState state) {
        var model = getModel(state);
        return model != null ? VertexFormat.IndexType.INT : state.indexType();
    }*/

    @Redirect(method = "upload", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexBuffer;uploadIndexBuffer(Lcom/mojang/blaze3d/vertex/MeshData$DrawState;Ljava/nio/ByteBuffer;)Lcom/mojang/blaze3d/systems/RenderSystem$AutoStorageIndexBuffer;"))
    private RenderSystem.AutoStorageIndexBuffer uploadIndexBuffer(VertexBuffer instance, MeshData.DrawState state, ByteBuffer buffer) {
        var model = getModel(state);
        if (model != null) {
            model.bindIndices();
            this.model = model;
            return null;
        }
        return uploadIndexBuffer(state, buffer);
    }

    @Redirect(method = "uploadIndexBuffer(Lcom/mojang/blaze3d/vertex/ByteBufferBuilder$Result;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;glBufferData(ILjava/nio/ByteBuffer;I)V", remap = false))
    private void uploadIndexBuffer(int i, ByteBuffer byteBuffer, int j) {
        if (model != null) {
            model.bindIndices();
            model = null;
            return;
        }
        RenderSystem.glBufferData(i, byteBuffer, j);
    }
}

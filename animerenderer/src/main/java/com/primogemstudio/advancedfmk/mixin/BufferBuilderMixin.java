package com.primogemstudio.advancedfmk.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.interfaces.BufferBuilderExt;
import com.primogemstudio.advancedfmk.interfaces.DrawStateExt;
import com.primogemstudio.advancedfmk.mmd.PMXModel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BufferBuilder.class)
public class BufferBuilderMixin implements BufferBuilderExt {
    @Shadow
    private boolean fullFormat;

    @Shadow
    public int vertices;

    @Shadow
    private VertexFormat format;

    @Shadow
    @Final
    public ByteBufferBuilder buffer;

    @Unique
    private PMXModel model;

    @Override
    public boolean fullFormat() {
        return fullFormat;
    }

    @Override
    public int padding() {
        return format.getVertexSize() - 36;
    }

    @Override
    public long resize(int size) {
        vertices = size;
        return buffer.reserve(vertices * format.getVertexSize());
    }

    @Nullable
    @Override
    public PMXModel getPMXModel() {
        return model;
    }

    @Override
    public void setPMXModel(@Nullable PMXModel model) {
        this.model = model;
    }

    @ModifyReturnValue(method = "storeMesh", at = @At("RETURN"))
    private MeshData storeRenderedBuffer(MeshData original) {
        if (original != null) {
            ((DrawStateExt) (Object) original.drawState()).setPMXModel(model);
            model = null;
            return original;
        }
        return null;
    }

    @Mixin(MeshData.DrawState.class)
    public static class DrawStateMixin implements DrawStateExt {
        @Unique
        private PMXModel model;

        @Override
        public PMXModel getPMXModel() {
            return model;
        }

        @Override
        public void setPMXModel(@Nullable PMXModel model) {
            this.model = model;
        }
    }
}

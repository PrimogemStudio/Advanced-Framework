package com.primogemstudio.advancedfmk.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.platform.MemoryTracker;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferVertexConsumer;
import com.mojang.blaze3d.vertex.DefaultedVertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.interfaces.BufferBuilderExt;
import com.primogemstudio.advancedfmk.interfaces.RenderedBufferExt;
import com.primogemstudio.advancedfmk.mmd.PMXModel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.ByteBuffer;


@Mixin(BufferBuilder.class)
public abstract class BufferBuilderMixin extends DefaultedVertexConsumer implements BufferVertexConsumer, BufferBuilderExt {
    @Shadow
    private int nextElementByte;
    @Shadow
    private boolean fullFormat;

    @Shadow
    public int vertices;

    @Shadow
    private VertexFormat format;

    @Shadow
    public ByteBuffer buffer;

    @Unique
    private PMXModel model;

    @Override
    public boolean fullFormat() {
        return fullFormat;
    }

    @Override
    public void bumpNxt(int val) {
        nextElementByte += val;
    }

    @Override
    public void setPtr(int ptr) {
        nextElementByte = ptr;
    }

    @Override
    public void submit() {
        nextElementByte = vertices * format.getVertexSize();
    }

    @Override
    public int padding() {
        return format.getVertexSize() - 36;
    }

    @Override
    public void resize() {
        var size = vertices * format.getVertexSize();
        if (buffer.capacity() < size) {
            buffer = MemoryTracker.resize(buffer, size);
            buffer.rewind();
        }
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

    @ModifyReturnValue(method = "storeRenderedBuffer", at = @At("RETURN"))
    private BufferBuilder.RenderedBuffer storeRenderedBuffer(BufferBuilder.RenderedBuffer original) {
        ((RenderedBufferExt) original).setPMXModel(model);
        model = null;
        return original;
    }

    @Mixin(BufferBuilder.RenderedBuffer.class)
    public static class RenderedBufferMixin implements RenderedBufferExt {
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

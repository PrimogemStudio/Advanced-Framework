package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferVertexConsumer;
import com.mojang.blaze3d.vertex.DefaultedVertexConsumer;
import com.primogemstudio.advancedfmk.interfaces.BufferBuilderExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BufferBuilder.class)
public abstract class BufferBuilderMixin extends DefaultedVertexConsumer implements BufferVertexConsumer, BufferBuilderExt {
    @Shadow private int nextElementByte;
    @Shadow private boolean fullFormat;

    @Override
    public boolean fullFormat() {
        return fullFormat;
    }

    @Override
    public void bumpNxt(int val) {
        nextElementByte += val;
    }
}

package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferVertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BufferBuilder.class)
public abstract class BufferBuilderMixin implements BufferVertexConsumer {
    @Shadow public abstract void nextElement();

    @Shadow public abstract void putFloat(int var1, float var2);
    public VertexConsumer uv(float u, float v) {
        this.putFloat(0, u);
        this.putFloat(4, v);
        this.nextElement();
        return this;
    }
    public VertexConsumer vertex(double x, double y, double z) {
        this.putFloat(0, (float)x);
        this.putFloat(4, (float)y);
        this.putFloat(8, (float)z);
        this.nextElement();
        return this;
    }

    @Override
    public VertexConsumer color(int red, int green, int blue, int alpha) {
        this.putByte(0, (byte)red);
        this.putByte(1, (byte)green);
        this.putByte(2, (byte)blue);
        this.putByte(3, (byte)alpha);
        this.nextElement();
        return this;
    }

    @Override
    public VertexConsumer uvShort(short u, short v, int index) {
        this.putShort(0, u);
        this.putShort(2, v);
        this.nextElement();
        return this;
    }

    @Override
    public VertexConsumer normal(float x, float y, float z) {
        this.putByte(0, BufferVertexConsumer.normalIntValue(x));
        this.putByte(1, BufferVertexConsumer.normalIntValue(y));
        this.putByte(2, BufferVertexConsumer.normalIntValue(z));
        this.nextElement();
        return this;
    }
}

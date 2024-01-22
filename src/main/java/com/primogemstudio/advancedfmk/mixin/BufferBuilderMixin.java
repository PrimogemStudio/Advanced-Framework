package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.vertex.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.ByteBuffer;

@Mixin(BufferBuilder.class)
public abstract class BufferBuilderMixin extends DefaultedVertexConsumer implements BufferVertexConsumer {
    @Shadow public abstract void putFloat(int var1, float var2);

    @Shadow private int vertices;

    @Shadow private ByteBuffer buffer;

    @Shadow private int nextElementByte;

    @Shadow private VertexFormat.Mode mode;

    @Shadow private VertexFormat format;

    @Shadow private int elementIndex;

    @Shadow @Nullable private VertexFormatElement currentElement;

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

    @Override
    public void endVertex() {
        ++this.vertices;
        if (this.mode == VertexFormat.Mode.LINES || this.mode == VertexFormat.Mode.LINE_STRIP) {
            int i = this.format.getVertexSize();
            this.buffer.put(this.nextElementByte, this.buffer, this.nextElementByte - i, i);
            this.nextElementByte += i;
            ++this.vertices;
        }
    }

    public void nextElement() {
        this.elementIndex = (this.elementIndex + 1) % this.format.getElements().size();
        this.nextElementByte += this.currentElement.getByteSize();
        this.currentElement = this.format.getElements().get(this.elementIndex);
        if (this.currentElement.getUsage() == VertexFormatElement.Usage.PADDING) {
            this.nextElement();
        }
        else if (this.defaultColorSet && this.currentElement.getUsage() == VertexFormatElement.Usage.COLOR) {
            color(this.defaultR, this.defaultG, this.defaultB, this.defaultA);
        }
    }
}

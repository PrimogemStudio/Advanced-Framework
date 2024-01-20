package com.primogemstudio.advancedfmk.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BufferBuilder.class)
public abstract class BufferBuilderOperatorImplMixin implements IBufferBuilderOperator {
    @Shadow public abstract void putFloat(int index, float floatValue);

    @Shadow public abstract void nextElement();

    @Shadow public abstract void putShort(int index, short shortValue);

    public void commitDirect(@NotNull Matrix4f mat, float x, float y, float z, float u, float v, int light) {
        Vector4f vector4f = mat.transform(new Vector4f(x, y, z, 1.0f));
        putFloat(0, vector4f.x);
        putFloat(4, vector4f.y);
        putFloat(8, vector4f.z);
        nextElement();
        putFloat(0, u);
        putFloat(4, v);
        nextElement();
        putShort(0, (short) (light & 0xFFFF));
        putShort(2, (short) (light >> 16 & 0xFFFF));
        nextElement();
    }
}

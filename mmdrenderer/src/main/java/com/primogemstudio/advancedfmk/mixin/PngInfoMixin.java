package com.primogemstudio.advancedfmk.mixin;

import net.minecraft.util.PngInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.nio.ByteBuffer;

@Mixin(PngInfo.class)
public class PngInfoMixin {
    /**
     * @author Jack253-png
     * @reason disable check
     */
    @Overwrite
    public static void validateHeader(ByteBuffer buffer) {
    }
}

package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.interfaces.SodiumBufferBuilderExt;
import me.jellysquid.mods.sodium.client.render.vertex.buffer.SodiumBufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(SodiumBufferBuilder.class)
public class SodiumBufferBuilderMixin implements SodiumBufferBuilderExt {
    @Unique
    private boolean finished;

    @Override
    public void mark() {
        finished = true;
    }

    @Override
    public void unmark() {
        finished = false;
    }

    @Inject(method = "isVertexFinished", at = @At("HEAD"), cancellable = true, remap = false)
    private void isVertexFinished(CallbackInfoReturnable<Boolean> cir) {
        if (finished) cir.setReturnValue(true);
    }
}

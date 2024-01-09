package com.primogemstudio.advancedfmk.render.filter

import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.pipeline.TextureTarget
import com.primogemstudio.advancedfmk.render.Shaders.FAST_GAUSSIAN_BLUR
import net.minecraft.client.Minecraft

class FastGaussianBlurFilter : Filter {
    private var enable = false
    override val target: RenderTarget
        get() = Companion.target

    override fun setArg(name: String?, value: Any?) {}
    override fun render(partialTicks: Float) {
        if (!enable) return
        FAST_GAUSSIAN_BLUR.setSamplerUniform("InputSampler", Companion.target)
        FAST_GAUSSIAN_BLUR.render(partialTicks)
    }

    override fun enable() {
        enable = true
    }

    override fun reset() {
        enable = false
    }

    companion object {
        private val target = TextureTarget(1, 1, true, Minecraft.ON_OSX)

        init {
            target.setClearColor(0f, 0f, 0f, 0f)
        }
    }
}

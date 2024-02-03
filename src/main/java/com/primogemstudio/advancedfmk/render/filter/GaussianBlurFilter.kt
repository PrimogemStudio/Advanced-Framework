package com.primogemstudio.advancedfmk.render.filter

import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.pipeline.TextureTarget
import com.primogemstudio.advancedfmk.render.Shaders.GAUSSIAN_BLUR
import net.minecraft.client.Minecraft
import kotlin.math.ceil
import kotlin.math.max

class GaussianBlurFilter : Filter {
    private var enable = false
    private var radius = 5
    private var enableFrostedGrass = false
    override val target: RenderTarget
        get() = Companion.target

    override fun setArg(name: String?, value: Any?) {
        when (name) {
            "Radius" -> radius = (value as Number).toInt()
            "EnableFrostGrass" -> enableFrostedGrass = value as Boolean
        }
    }

    override fun render(partialTicks: Float) {
        if (!enable) return
        GAUSSIAN_BLUR.setSamplerUniform("InputSampler", Companion.target)
        GAUSSIAN_BLUR.setUniformValue("Radius", (ceil(max(4.0, radius.toDouble()) / 2) * 2).toInt())
        GAUSSIAN_BLUR.setUniformValue("DigType", if (enableFrostedGrass) 1 else 0)
        GAUSSIAN_BLUR.render(partialTicks)
    }

    override fun enable() {
        enable = true
    }

    override fun reset() {
        enable = false
        radius = 0
    }

    companion object {
        private val target = TextureTarget(1, 1, true, Minecraft.ON_OSX)

        init {
            target.setClearColor(0f, 0f, 0f, 0f)
        }
    }
}

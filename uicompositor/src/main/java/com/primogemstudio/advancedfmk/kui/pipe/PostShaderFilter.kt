package com.primogemstudio.advancedfmk.kui.pipe

import com.mojang.blaze3d.pipeline.RenderTarget
import com.primogemstudio.advancedfmk.kui.GlobalData
import org.ladysnake.satin.api.managed.ManagedShaderEffect

class PostShaderFilter(
    val shader: ManagedShaderEffect
) : FilterBase {
    var delegate: RenderTarget? = null
    override fun init() {
        (delegate?: COMPOSE_FRAME).clear(IS_OSX)
        (delegate?: COMPOSE_FRAME).bindWrite(true)
    }

    override fun apply(data: GlobalData) {
        shader.setSamplerUniform("InputSampler", (delegate?: COMPOSE_FRAME))
        shader.render(data.tick)

        delegate = null
    }

    override fun arg(key: String, a: Int) = shader.setUniformValue(key, a)
    override fun arg(key: String, a: Int, b: Int) = shader.setUniformValue(key, a, b)
    override fun arg(key: String, a: Int, b: Int, c: Int) = shader.setUniformValue(key, a, b, c)
    override fun arg(key: String, a: Int, b: Int, c: Int, d: Int) = shader.setUniformValue(key, a, b, c, d)

    override fun arg(key: String, a: Float) = shader.setUniformValue(key, a)
    override fun arg(key: String, a: Float, b: Float) = shader.setUniformValue(key, a, b)
    override fun arg(key: String, a: Float, b: Float, c: Float) = shader.setUniformValue(key, a, b, c)
    override fun arg(key: String, a: Float, b: Float, c: Float, d: Float) = shader.setUniformValue(key, a, b, c, d)
}
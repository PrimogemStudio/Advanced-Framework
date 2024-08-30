package com.primogemstudio.advancedfmk.kui.pipe

import com.mojang.blaze3d.pipeline.RenderTarget
import com.primogemstudio.advancedfmk.kui.GlobalData
import org.joml.*
import org.ladysnake.satin.api.managed.ManagedShaderEffect

class PostShaderFilter(
    val shader: ManagedShaderEffect,
    override var args: MutableMap<String, Any>
) : FilterBase {
    var delegate: RenderTarget? = null
    override fun init() {
        (delegate?: COMPOSE_FRAME).clear(IS_OSX)
        (delegate?: COMPOSE_FRAME).bindWrite(true)
    }

    override fun apply(data: GlobalData) {
        shader.setSamplerUniform("InputSampler", (delegate?: COMPOSE_FRAME))
        args.forEach {
            when (it.value) {
                is Float -> arg(it.key, it.value as Float)
                is Int -> arg(it.key, it.value as Int)
                is Vector2f -> (it.value as Vector2f).apply { arg(it.key, x, y) }
                is Vector2i -> (it.value as Vector2i).apply { arg(it.key, x, y) }
                is Vector3f -> (it.value as Vector3f).apply { arg(it.key, x, y, z) }
                is Vector3i -> (it.value as Vector3i).apply { arg(it.key, x, y, z) }
                is Vector4f -> (it.value as Vector4f).apply { arg(it.key, x, y, z, w) }
                is Vector4i -> (it.value as Vector4i).apply { arg(it.key, x, y, z, w) }
                else -> {}
            }
        }

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
    override fun arg(
        key: String,
        aa: Float,
        ab: Float,
        ac: Float,
        ad: Float,
        ba: Float,
        bb: Float,
        bc: Float,
        bd: Float,
        ca: Float,
        cb: Float,
        cc: Float,
        cd: Float,
        da: Float,
        db: Float,
        dc: Float,
        dd: Float
    ) {
        shader.setUniformValue(key, Matrix4f(
            aa, ab, ac, ad,
            ba, bb, bc, bd,
            ca, cb, cc, cd,
            da, db, dc, dd
        ))
    }
}
package com.primogemstudio.advancedfmk.mixin

import org.joml.Matrix4f

interface IBufferBuilderOperator {
    fun commitDirect(mat: Matrix4f, x: Float, y: Float, z: Float, u: Float, v: Float, light: Int)
}
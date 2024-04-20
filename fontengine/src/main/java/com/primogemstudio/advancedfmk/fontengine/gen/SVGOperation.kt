package com.primogemstudio.advancedfmk.fontengine.gen

import org.joml.Vector2f

data class SVGOperation(
    val type: OpType, val target: Vector2f, val control1: Vector2f? = null, val control2: Vector2f? = null
) {
    enum class OpType {
        MOVE, LINE, CONIC, CUBIC
    }
}
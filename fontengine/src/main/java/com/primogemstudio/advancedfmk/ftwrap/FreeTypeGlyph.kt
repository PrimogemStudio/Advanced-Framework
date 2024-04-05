package com.primogemstudio.advancedfmk.ftwrap

import org.joml.Vector2f

data class FreeTypeGlyph(
    val whscale: Float,
    val vertices: List<Vector2f>,
    val indices: List<Int>
)
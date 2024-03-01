package com.primogemstudio.advancedfmk.render.uiframework.ui

import glm_.vec2.Vec2

data class GlobalVars(
    var screen_size: Vec2 = Vec2(),
    var tick: Float = 0f
) {
    fun toMap(): Map<String, Float> {
        return mapOf(
            Pair("screen_width", screen_size.x),
            Pair("screen_height", screen_size.y)
        )
    }
}
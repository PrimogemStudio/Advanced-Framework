package com.primogemstudio.advancedfmk.render.uiframework.ui

import org.joml.Vector2f

val data = GlobalVars()
data class GlobalVars(
    var screen_size: Vector2f = Vector2f(),
    var tick: Float = 0f
) {
    fun toMap(): Map<String, Float> {
        return mapOf(
            Pair("screen_width", screen_size.x),
            Pair("screen_height", screen_size.y),
            Pair("tick", tick)
        )
    }
}
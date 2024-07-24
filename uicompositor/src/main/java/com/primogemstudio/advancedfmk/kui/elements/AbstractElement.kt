package com.primogemstudio.advancedfmk.kui.elements

import org.joml.Vector2f

abstract class RealElement(open var id: String, open var pos: Vector2f) : UIElement {
    private val internalMap = mutableMapOf<String, Any>()
    operator fun get(s: String): Any? = internalMap[s]
    operator fun set(s: String, v: Any) {
        internalMap[s] = v
    }
}
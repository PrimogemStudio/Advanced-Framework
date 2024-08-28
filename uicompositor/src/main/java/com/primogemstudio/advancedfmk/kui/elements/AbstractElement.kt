package com.primogemstudio.advancedfmk.kui.elements

import com.primogemstudio.advancedfmk.kui.GlobalData
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.joml.Vector2f

abstract class RealElement(open var id: String, open var pos: Vector2f) : UIElement {
    val renderLock = Mutex()
    private val internalMap = mutableMapOf<String, Any>()
    operator fun get(s: String): Any? = internalMap[s]
    operator fun set(s: String, v: Any) {
        internalMap[s] = v
    }

    final override fun render(data: GlobalData) {
        runBlocking { renderLock.withLock { renderActual(data) } }
    }
    protected abstract fun renderActual(data: GlobalData)
}
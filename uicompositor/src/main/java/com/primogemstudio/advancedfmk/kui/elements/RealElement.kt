package com.primogemstudio.advancedfmk.kui.elements

import com.primogemstudio.advancedfmk.kui.GlobalData
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.joml.Vector2f
import org.joml.Vector4i

abstract class RealElement(open var id: String, open var pos: Vector2f) : UIElement {
    val renderLock = Mutex()
    var clip: Vector4i? = null
    private val internalMap = mutableMapOf<String, Any>()
    operator fun get(s: String): Any? = internalMap[s]
    operator fun set(s: String, v: Any) {
        internalMap[s] = v
    }

    final override fun render(data: GlobalData) {
        runBlocking { renderLock.withLock {
            if (clip != null) data.graphics.enableScissor(clip!!.x, clip!!.y, clip!!.x + clip!!.z, clip!!.y + clip!!.w)
            renderActual(data)
            if (clip != null) data.graphics.disableScissor()
        }}
    }
    protected abstract fun renderActual(data: GlobalData)
}
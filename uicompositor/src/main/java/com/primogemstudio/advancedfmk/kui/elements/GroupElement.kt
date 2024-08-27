package com.primogemstudio.advancedfmk.kui.elements

import com.primogemstudio.advancedfmk.kui.GlobalData
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.joml.Vector2f

class GroupElement(override var id: String, subElements: List<UIElement>) : RealElement(id, Vector2f(0f)) {
    private val cacheMap = mutableMapOf<String, RealElement>()
    val lock = Mutex(false)
    var subElements: List<UIElement> = subElements
        set(v) {
            cacheMap.clear()
            field = v
        }

    override fun render(data: GlobalData) {
        runBlocking {
            lock.withLock {
                subElements.forEach { it.render(data) }
            }
        }
    }

    override fun subElement(id: String): RealElement? = if (cacheMap.containsKey(id)) cacheMap[id] else subElements.filterIsInstance<RealElement>().find { it.id == id }?.apply { cacheMap[id] = this }
}
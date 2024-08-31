package com.primogemstudio.advancedfmk.kui.elements

import com.primogemstudio.advancedfmk.kui.GlobalData
import org.joml.Vector2f
import org.joml.Vector4i

class GroupElement(override var id: String, subElements: List<UIElement>) : RealElement(id, Vector2f(0f)) {
    private val cacheMap = mutableMapOf<String, RealElement>()
    var subElements: List<UIElement> = subElements
        set(v) {
            cacheMap.clear()
            field = v
        }

    override fun renderActual(data: GlobalData) {
        subElements.forEach { it.render(data) }
    }

    override fun renderWithoutFilter(data: GlobalData) {
        subElements.forEach {
            if (it is TextElement) return@forEach
            if (it is RealElement) it.renderWithoutFilter(data) else it.render(data)
        }
    }

    override fun subElement(id: String): RealElement? = if (cacheMap.containsKey(id)) cacheMap[id] else subElements.filterIsInstance<RealElement>().find { it.id == id }?.apply { cacheMap[id] = this }
}
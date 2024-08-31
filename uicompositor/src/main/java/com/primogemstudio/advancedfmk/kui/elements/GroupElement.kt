package com.primogemstudio.advancedfmk.kui.elements

import com.primogemstudio.advancedfmk.kui.GlobalData
import org.joml.Vector2f
import org.joml.Vector4i

class GroupElement(override var id: String, subElements: List<UIElement>) : RealElement(id, Vector2f(0f)) {
    private val cacheMap = mutableMapOf<String, RealElement>()
    var clip: Vector4i? = null
    var subElements: List<UIElement> = subElements
        set(v) {
            cacheMap.clear()
            field = v
        }

    override fun renderActual(data: GlobalData) {
        if (clip != null) data.graphics.enableScissor(clip!!.x, clip!!.y, clip!!.x + clip!!.z, clip!!.y + clip!!.w)
        subElements.forEach { it.render(data) }
        if (clip != null) data.graphics.disableScissor()
    }

    override fun subElement(id: String): RealElement? = if (cacheMap.containsKey(id)) cacheMap[id] else subElements.filterIsInstance<RealElement>().find { it.id == id }?.apply { cacheMap[id] = this }
}
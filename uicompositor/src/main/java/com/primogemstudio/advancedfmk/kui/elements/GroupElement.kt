package com.primogemstudio.advancedfmk.kui.elements

import com.primogemstudio.advancedfmk.kui.GlobalData
import org.joml.Vector2f

class GroupElement(override var id: String, var subElements: List<UIElement>) : RealElement(id, Vector2f(0f)) {
    override fun render(data: GlobalData) {
        subElements.forEach { it.render(data) }
    }

    override fun subElement(id: String): UIElement? = subElements.filterIsInstance<RealElement>().find { it.id == id }
}
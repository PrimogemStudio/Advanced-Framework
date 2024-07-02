package com.primogemstudio.advancedfmk.render.kui.elements

import com.primogemstudio.advancedfmk.render.kui.GlobalData

class GroupElement(var subElements: List<UIElement>) : UIElement {
    override fun render(data: GlobalData) {
        subElements.forEach { it.render(data) }
    }
}
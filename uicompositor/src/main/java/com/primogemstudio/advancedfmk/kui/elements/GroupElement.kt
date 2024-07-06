package com.primogemstudio.advancedfmk.kui.elements

import com.primogemstudio.advancedfmk.kui.GlobalData

class GroupElement(var subElements: List<UIElement>) : UIElement {
    override fun render(data: GlobalData) {
        subElements.forEach { it.render(data) }
    }
}
package com.primogemstudio.advancedfmk.render.kui.elements

import com.primogemstudio.advancedfmk.render.kui.pipe.FilterBase

interface FilteredElement {
    fun filter(): FilterBase?
}
package com.primogemstudio.advancedfmk.kui.elements

import com.primogemstudio.advancedfmk.kui.pipe.FilterBase

interface FilteredElement {
    fun filter(): FilterBase?
}
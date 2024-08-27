package com.primogemstudio.advancedfmk.kui.elements

import com.primogemstudio.advancedfmk.kui.GlobalData
import kotlin.reflect.KClass

interface UIElement {
    fun render(data: GlobalData)
    fun subElement(id: String): UIElement?
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> subElement(id: String, cls: KClass<T>): T = subElement(id) as T
}
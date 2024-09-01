package com.primogemstudio.advancedfmk.kui.elements

import com.mojang.blaze3d.pipeline.TextureTarget
import com.primogemstudio.advancedfmk.kui.GlobalData
import kotlin.reflect.KClass

interface UIElement {
    fun render(data: GlobalData)
    fun renderWithoutFilter(data: GlobalData)
    fun renderWithClip(data: GlobalData, texture: TextureTarget)
    fun subElement(id: String): UIElement?
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> subElement(id: String, cls: KClass<T>): T = subElement(id) as T
}
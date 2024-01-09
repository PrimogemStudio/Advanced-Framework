package com.primogemstudio.advancedfmk.render.filter

import com.mojang.blaze3d.pipeline.RenderTarget

interface Filter {
    val target: RenderTarget?
    fun setArg(name: String?, value: Any?)
    fun render(partialTicks: Float)
    fun enable()
    fun reset()
}

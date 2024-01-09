package com.primogemstudio.advancedfmk.render

import com.primogemstudio.advancedfmk.render.FilterTypes.FAST_GAUSSIAN_BLUR
import com.primogemstudio.advancedfmk.render.FilterTypes.GAUSSIAN_BLUR
import com.primogemstudio.advancedfmk.render.filter.FastGaussianBlurFilter
import com.primogemstudio.advancedfmk.render.filter.Filter
import com.primogemstudio.advancedfmk.render.filter.FilterType
import com.primogemstudio.advancedfmk.render.filter.GaussianBlurFilter
import net.minecraft.client.Minecraft

object RenderQueue {
    private val renderResource = RenderResource()
    private val filters: MutableMap<FilterType, Filter> = HashMap()

    init {
        register(GAUSSIAN_BLUR, GaussianBlurFilter())
        register(FAST_GAUSSIAN_BLUR, FastGaussianBlurFilter())
    }

    @JvmStatic
    fun init(width: Int, height: Int) {
        val flag = width != renderResource.width || height != renderResource.height
        if (flag) {
            renderResource.width = width
            renderResource.height = height
        }
        for (f in filters.values) {
            if (flag) f.target?.resize(width, height, Minecraft.ON_OSX)
            f.target?.clear(Minecraft.ON_OSX)
            f.reset()
        }
    }

    @JvmStatic
    fun register(type: FilterType, filter: Filter) {
        filters[type] = filter
    }

    @JvmStatic
    fun post(partialTicks: Float) {
        for (f in filters.values) {
            f.render(partialTicks)
        }
    }

    @JvmStatic
    fun flush(partialTicks: Float) {
        post(partialTicks)
        for (f in filters.values) {
            f.target?.clear(Minecraft.ON_OSX)
            f.reset()
        }
        Minecraft.getInstance().mainRenderTarget.bindWrite(true)
    }

    @JvmStatic
    fun setFilterArg(type: FilterType, name: String?, value: Any?) {
        val filter = filters[type]
        filter!!.setArg(name, value)
    }

    @JvmStatic
    fun draw(renderable: Renderable) {
        renderable.render(renderResource)
    }

    @JvmStatic
    fun draw(renderable: Renderable, type: FilterType) {
        val filter = filters[type]
        val target = filter?.target
        target?.bindWrite(true)
        renderable.render(renderResource)
        target?.unbindWrite()
        filter?.enable()
        Minecraft.getInstance().mainRenderTarget.bindWrite(true)
    }
}

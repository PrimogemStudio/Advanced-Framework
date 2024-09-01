package com.primogemstudio.advancedfmk.kui.elements

import com.mojang.blaze3d.pipeline.TextureTarget
import com.primogemstudio.advancedfmk.kui.GlobalData
import com.primogemstudio.advancedfmk.kui.pipe.CLIP_FRAME
import com.primogemstudio.advancedfmk.kui.pipe.IS_OSX
import net.minecraft.client.Minecraft
import org.joml.Vector2f

class GroupElement(override var id: String, subElements: List<UIElement>) : RealElement(id, Vector2f(0f)) {
    private val cacheMap = mutableMapOf<String, RealElement>()
    var clip: GroupElement? = null
    private var subElements: List<UIElement> = subElements
        set(v) {
            cacheMap.clear()
            field = v
        }

    override fun renderActual(data: GlobalData) {
        if (clip != null) {
            CLIP_FRAME.clear(IS_OSX)
            CLIP_FRAME.bindWrite(true)
            clip!!.renderWithoutFilter(data)
            Minecraft.getInstance().mainRenderTarget.bindWrite(true)
            renderWithClip(data, CLIP_FRAME)
        }
        else subElements.forEach { it.render(data) }
    }

    override fun renderWithoutFilter(data: GlobalData) {
        subElements.forEach { it.renderWithoutFilter(data) }
    }

    override fun renderWithClip(data: GlobalData, texture: TextureTarget) {
        subElements.forEach { it.renderWithClip(data, texture) }
    }

    override fun subElement(id: String): RealElement? = if (cacheMap.containsKey(id)) cacheMap[id] else subElements.filterIsInstance<RealElement>().find { it.id == id }?.apply { cacheMap[id] = this }
}
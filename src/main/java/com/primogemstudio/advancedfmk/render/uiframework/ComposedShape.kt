package com.primogemstudio.advancedfmk.render.uiframework

import com.primogemstudio.advancedfmk.render.RenderResource
import com.primogemstudio.advancedfmk.render.shape.AbstractBackdropableShape
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import org.joml.Matrix4f

open class ComposedShape(x: Float, y: Float, w: Float, h: Float, message: Component?) :
    AbstractBackdropableShape(x.toInt(), y.toInt(), w.toInt(), h.toInt(), message) {
    private var matrix = Matrix4f()
    private val internalShapes = mutableMapOf<ResourceLocation, AbstractBackdropableShape>()
    fun registerShape(id: ResourceLocation, spe: AbstractBackdropableShape) { internalShapes[id] = spe }
    fun removeShape(id: ResourceLocation) { internalShapes.remove(id) }
    fun <T: AbstractBackdropableShape> fetchShape(id: ResourceLocation, clazz: Class<T>): T = internalShapes[id] as T
    var locactionx: Int
        get() = this.x
        set(x) { this.x = x }
    var locactiony: Int
        get() = this.y
        set(y) { this.y = y }

    var locactionw: Int
        get() = this.width
        set(w) { this.width = w }

    var locactionh: Int
        get() = this.height
        set(h) { this.height = h }
    override fun updateStack(matrix: Matrix4f) { this.matrix = matrix }
    override fun render(res: RenderResource?) = internalShapes.forEach { it.value.render(res) }
    override fun resize(x: Float, y: Float, w: Float, h: Float): ComposedShape {
        locactionx = x.toInt()
        locactiony = y.toInt()
        locactionw = w.toInt()
        locactionh = h.toInt()
        return this
    }

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) = internalShapes.forEach { it.value.renderWidget(guiGraphics, mouseX, mouseY, partialTick) }
}
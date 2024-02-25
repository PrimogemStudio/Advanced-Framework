package com.primogemstudio.advancedfmk.render.shape

import com.primogemstudio.advancedfmk.render.RenderQueue.draw
import com.primogemstudio.advancedfmk.render.RenderQueue.flush
import com.primogemstudio.advancedfmk.render.RenderQueue.setFilterArg
import com.primogemstudio.advancedfmk.render.Renderable
import com.primogemstudio.advancedfmk.render.filter.FilterType
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.narration.NarratedElementType
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component
import org.joml.Matrix4f

abstract class AbstractBackdropableShape(x: Int, y: Int, width: Int, height: Int, message: Component?) :
    AbstractWidget(x, y, width, height, message?: Component.literal("")), Renderable {
    private var type: FilterType? = null
    private val data: MutableMap<String, Any> = HashMap()
    abstract fun resize(x: Float, y: Float, w: Float, h: Float): AbstractBackdropableShape
    fun setType(type: FilterType?): AbstractBackdropableShape {
        this.type = type
        return this
    }

    fun addData(s: String, o: Any): AbstractBackdropableShape {
        data[s] = o
        return this
    }

    public override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        updateStack(guiGraphics.pose().last().pose())
        if (type == null) draw(this) else {
            data.forEach { (s: String?, o: Any?) ->
                setFilterArg(
                    type!!, s, o
                )
            }
            draw(this, type!!)
        }
        flush(partialTick)
    }

    protected abstract fun updateStack(matrix: Matrix4f)
    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.USAGE, message)
    }
}

package com.primogemstudio.advancedfmk.render.kui

import com.primogemstudio.advancedfmk.render.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.render.kui.elements.RectangleElement
import net.minecraft.resources.ResourceLocation
import org.joml.Vector2f
import org.joml.Vector4f

class KUITest {
    val elem = GroupElement(
        listOf(
            RectangleElement(
                Vector2f(0f, 0f),
                Vector2f(100f, 100f),
                Vector4f(1f),
                20f,
                0f,
                0.006f,
                ResourceLocation.parse("advancedfmk:ui/textures/microsoft.png")
            )
        )
    )

    fun render(gd: GlobalData) {
        // gd.graphics.drawString(Minecraft.getInstance().font, "Hello world, KUI!", 0, 0, 0xffffff)
        elem.render(gd)
    }
}
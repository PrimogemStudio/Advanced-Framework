package com.primogemstudio.advancedfmk.render.kui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics

class KUITest {
    fun render(graphics: GuiGraphics) {
        graphics.drawString(Minecraft.getInstance().font, "Hello world, KUI!", 0, 0, 0xffffff)
    }
}
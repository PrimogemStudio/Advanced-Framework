package com.primogemstudio.advancedfmk.kui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics

data class GlobalData(
    val screenWidth: Int,
    val screenHeight: Int,
    val tick: Float,
    val graphics: GuiGraphics
) {
    companion object {
        @JvmStatic
        fun genData(graphics: GuiGraphics, tick: Float): GlobalData = GlobalData(
            Minecraft.getInstance().window.guiScaledWidth,
            Minecraft.getInstance().window.guiScaledHeight,
            tick,
            graphics
        )
    }
}

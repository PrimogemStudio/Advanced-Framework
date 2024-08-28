package com.primogemstudio.advancedfmk.screen

import com.primogemstudio.advancedfmk.kui.GlobalData.Companion.genData
import com.primogemstudio.advancedfmk.kui.instance
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

class TestSnakeDualScreen: Screen(Component.literal("Test!")) {
    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBlurredBackground(partialTick)
        instance.elem.render(genData(guiGraphics, partialTick))
    }
}
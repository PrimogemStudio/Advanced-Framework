package com.primogemstudio.advancedfmk.screen

import com.primogemstudio.advancedfmk.kui.GlobalData.Companion.genData
import com.primogemstudio.advancedfmk.kui.instance
import com.primogemstudio.advancedfmk.kui.test.snakedual.Snake.Companion.DOWN
import com.primogemstudio.advancedfmk.kui.test.snakedual.Snake.Companion.LEFT
import com.primogemstudio.advancedfmk.kui.test.snakedual.Snake.Companion.RIGHT
import com.primogemstudio.advancedfmk.kui.test.snakedual.Snake.Companion.UP
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import org.lwjgl.glfw.GLFW

class TestSnakeDualScreen: Screen(Component.literal("Test!")) {
    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        instance.elem.render(genData(guiGraphics, partialTick))
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        return super.keyPressed(keyCode, scanCode, modifiers).apply {
            if (keyCode == GLFW.GLFW_KEY_A) instance.snake.worm.crp(LEFT)
            if (keyCode == GLFW.GLFW_KEY_D) instance.snake.worm.crp(RIGHT)
            if (keyCode == GLFW.GLFW_KEY_S) instance.snake.worm.crp(DOWN)
            if (keyCode == GLFW.GLFW_KEY_W) instance.snake.worm.crp(UP)
        }
    }
}
package com.primogemstudio.advancedfmk.screen

import com.primogemstudio.advancedfmk.kui.GlobalData
import com.primogemstudio.advancedfmk.kui.animation.*
import com.primogemstudio.advancedfmk.kui.elements.GeometryLineElement
import com.primogemstudio.advancedfmk.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.kui.elements.RectangleElement
import com.primogemstudio.advancedfmk.kui.pipe.guiScaledHeight
import com.primogemstudio.advancedfmk.kui.pipe.guiScaledWidth
import com.primogemstudio.advancedfmk.kui.yaml.YamlParser
import com.primogemstudio.advancedfmk.kui.yaml.jvm.YamlCompiler
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

class TimelineTestScreen: Screen(Component.literal("Test!")) {
    companion object {
        var elem = YamlCompiler(
            YamlParser.parse(
                String(
                    TestSnakeDualScreen::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/timeline.yaml")!!.readAllBytes()
                )
            )).build(GroupElement::class)

        private val animations: List<AnimationEvent<Float>> = listOf(
            ElemLockEvent(elem),
            CustomAnimationEvent {
                elem.subElement("rect_panel", RectangleElement::class).apply {
                    size.set(guiScaledWidth / 3f, guiScaledHeight / 1.3f)
                    pos.set(guiScaledWidth / 2f - guiScaledWidth / 6f, guiScaledHeight / 2f - guiScaledHeight / 2.6f)
                }
            },
            ElemUnlockEvent(elem)
        )

        init {
            EventLoop.objects.add(AnimatedObject(elem, animations))
        }
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        elem.render(GlobalData.genData(guiGraphics, partialTick))
    }
}
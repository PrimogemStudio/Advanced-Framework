package com.primogemstudio.advancedfmk.screen

import com.primogemstudio.advancedfmk.kui.GlobalData
import com.primogemstudio.advancedfmk.kui.animation.*
import com.primogemstudio.advancedfmk.kui.elements.GeometryLineElement
import com.primogemstudio.advancedfmk.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.kui.elements.RectangleElement
import com.primogemstudio.advancedfmk.kui.elements.TextElement
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
                val height = guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f

                elem.subElement("rect_panel", RectangleElement::class).apply {
                    size.set(guiScaledWidth / 2.2f, guiScaledHeight / 1.3f + guiScaledHeight / 10f)
                    pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f, height)
                }
                elem.subElement("text_name", TextElement::class).apply {
                    text = "Coder2"
                    pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f + 15, height + 5)
                }
                elem.subElement("text_sign", TextElement::class).apply {
                    text = "Advanced Framework 主开发者"
                    pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f + 15, height + 18)
                }
                elem.subElement("name_split", GeometryLineElement::class).apply {
                    vertices[0].set(guiScaledWidth / 2f - guiScaledWidth / 4.4f, height + 30)
                    vertices[1].set(guiScaledWidth / 2f + guiScaledWidth / 4.4f, height + 30)
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
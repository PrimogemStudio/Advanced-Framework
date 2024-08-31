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
import org.joml.Vector2f

class TimelineTestScreen: Screen(Component.literal("Test!")) {
    companion object {
        var elem = YamlCompiler(
            YamlParser.parse(
                String(
                    TestSnakeDualScreen::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/chatting_panel.yaml")!!.readAllBytes()
                )
            )).build(GroupElement::class)

        var target = YamlCompiler(
            YamlParser.parse(
                String(
                    TestSnakeDualScreen::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/chatting_line.yaml")!!.readAllBytes()
                )
            )).build(GroupElement::class)

        fun setPos() {
            val yOffset = 0f
            var t: Vector2f?
            target.subElement("rect_avatar", RectangleElement::class).apply {
                pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f + 6, guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + yOffset)
            }
            target.subElement("text_name", TextElement::class).apply {
                text = "Coder2"
                pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f + 16 + 2 * 6, guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + yOffset)
            }
            target.subElement("text_main", TextElement::class).apply {
                text = "这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！"
                maxLineWidth = (guiScaledWidth / 2.2f).toInt() - 30 - 12 * 2
                t = TextElement.FONT.getTextBorder(text, textsize, maxLineWidth).add(12f, 8f)
                pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f + 16 + 2 * 6 + 6, guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + 12 + yOffset + 4)
            }
            target.subElement("rect_background", RectangleElement::class).apply {
                pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f + 16 + 2 * 6, guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + 12 + yOffset)
                size.set(t)
            }
        }

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
                    pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f + 15, height + 17.5f)
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
        setPos()
        target.render(GlobalData.genData(guiGraphics, partialTick))
    }
}
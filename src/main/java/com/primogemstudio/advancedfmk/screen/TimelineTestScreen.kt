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
import net.minecraft.resources.ResourceLocation
import org.joml.Vector2f

class TimelineTestScreen: Screen(Component.literal("Test!")) {
    companion object {
        val AVATAR1 = ResourceLocation.parse("advancedfmk:ui/textures/avatars/jack253-png.png")
        val AVATAR2 = ResourceLocation.parse("advancedfmk:ui/textures/avatars/hackermdch.png")

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

        fun setPos(yOffset: Float, name: String, content: String, self: Boolean = true): Float {
            if (self) {
                var t: Vector2f?
                target.subElement("rect_avatar", RectangleElement::class).apply {
                    pos.set(
                        guiScaledWidth / 2f - guiScaledWidth / 4.4f + 6,
                        guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + yOffset
                    )
                    texturePath = AVATAR1
                }
                target.subElement("text_name", TextElement::class).apply {
                    text = name
                    pos.set(
                        guiScaledWidth / 2f - guiScaledWidth / 4.4f + 16 + 2 * 6,
                        guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + yOffset
                    )
                }
                target.subElement("text_main", TextElement::class).apply {
                    text =
                        content
                    maxLineWidth = (guiScaledWidth / 2.2f).toInt() - 30 - 12 * 2
                    t = TextElement.FONT.getTextBorder(text, textsize, maxLineWidth).add(12f, 8f)
                    pos.set(
                        guiScaledWidth / 2f - guiScaledWidth / 4.4f + 16 + 2 * 6 + 6,
                        guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + 12 + yOffset + 2
                    )
                }
                target.subElement("rect_background", RectangleElement::class).apply {
                    pos.set(
                        guiScaledWidth / 2f - guiScaledWidth / 4.4f + 16 + 2 * 6,
                        guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + 12 + yOffset
                    )
                    size.set(t)
                    color.set(1f, 1f, 1f, 1f)
                }
                return t!!.y + 12 + 8
            }
            else {
                var t: Vector2f?
                target.subElement("rect_avatar", RectangleElement::class).apply {
                    pos.set(
                        guiScaledWidth / 2f + guiScaledWidth / 4.4f - 6 - 16,
                        guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + yOffset
                    )
                    texturePath = AVATAR2
                }
                target.subElement("text_name", TextElement::class).apply {
                    text = name
                    val r = TextElement.FONT.getTextBorder(text, textsize)
                    pos.set(
                        guiScaledWidth / 2f + guiScaledWidth / 4.4f - 16 - 2 * 6 - r.x,
                        guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + yOffset
                    )
                }
                target.subElement("text_main", TextElement::class).apply {
                    text =
                        content
                    maxLineWidth = (guiScaledWidth / 2.2f).toInt() - 30 - 12 * 2
                    t = TextElement.FONT.getTextBorder(text, textsize, maxLineWidth).add(12f, 8f)
                    pos.set(
                        guiScaledWidth / 2f + guiScaledWidth / 4.4f - 16 - 2 * 6 + 6 - t!!.x,
                        guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + 12 + yOffset + 2
                    )
                }
                target.subElement("rect_background", RectangleElement::class).apply {
                    pos.set(
                        guiScaledWidth / 2f + guiScaledWidth / 4.4f - 16 - 2 * 6 - t!!.x,
                        guiScaledHeight / 2f - guiScaledHeight / 2.6f - guiScaledHeight / 10f + 30 + 12 + yOffset
                    )
                    size.set(t)
                    color.set(1f, 0.8f, 0.05f, 1f)
                }
                return t!!.y + 12 + 8
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
                elem.subElement("rect_panel_clip", RectangleElement::class).apply {
                    size.set(guiScaledWidth / 2.2f, guiScaledHeight / 1.3f + guiScaledHeight / 10f - 30 - 2)
                    pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f, height + 30 + 2)
                }
                elem.subElement("texts", GroupElement::class).apply {
                    subElement("text_name", TextElement::class).apply {
                        text = "Coder2"
                        pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f + 15, height + 5)
                    }
                    subElement("text_sign", TextElement::class).apply {
                        text = "Advanced Framework 主开发者"
                        pos.set(guiScaledWidth / 2f - guiScaledWidth / 4.4f + 15, height + 17.5f)
                    }
                    clip = elem.subElement("rect_panel", RectangleElement::class)
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
        target.clip = elem.subElement("rect_panel_clip")
        // var base: Float = Linear.gen(System.currentTimeMillis().toDouble() % 2000 / 2000).toFloat() * -40f
        var base = 4f
        base += setPos(base, "Coder2", "Test!")
        target.render(GlobalData.genData(guiGraphics, partialTick))
        base += setPos(base, "hackermdch", "这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！这是一行长文本！", false)
        target.render(GlobalData.genData(guiGraphics, partialTick))
        base += setPos(base, "Coder2", "Test!")
        target.render(GlobalData.genData(guiGraphics, partialTick))
    }
}
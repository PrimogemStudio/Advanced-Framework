package com.primogemstudio.advancedfmk.kui

import com.mojang.blaze3d.platform.InputConstants
import com.primogemstudio.advancedfmk.kui.animation.*
import com.primogemstudio.advancedfmk.kui.elements.GeometryLineElement
import com.primogemstudio.advancedfmk.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.kui.elements.RectangleElement
import com.primogemstudio.advancedfmk.kui.pipe.mouseX
import com.primogemstudio.advancedfmk.kui.pipe.mouseY
import com.primogemstudio.advancedfmk.kui.test.snakedual.Main
import com.primogemstudio.advancedfmk.kui.test.snakedual.Move.Companion.DOWN
import com.primogemstudio.advancedfmk.kui.test.snakedual.Move.Companion.LEFT
import com.primogemstudio.advancedfmk.kui.test.snakedual.Move.Companion.RIGHT
import com.primogemstudio.advancedfmk.kui.test.snakedual.Move.Companion.UP
import com.primogemstudio.advancedfmk.kui.yaml.YamlParser
import com.primogemstudio.advancedfmk.kui.yaml.jvm.YamlCompiler
import net.minecraft.client.Minecraft
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW

val instance = KUITest()

class KUITest {
    companion object {
        var res = YamlCompiler(YamlParser.parse(
            String(
                KUITest::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/resourcepack_icon.yaml")!!.readAllBytes()
            )
        )).build() as GroupElement
    }

    var elem = YamlCompiler(YamlParser.parse(
        String(
            KUITest::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/test.yaml")!!.readAllBytes()
        )
    )).build() as GroupElement

    fun reload() {
        elem = YamlCompiler(YamlParser.parse(
            String(
                KUITest::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/test.yaml")!!.readAllBytes()
            )
        )).build() as GroupElement
        res = YamlCompiler(YamlParser.parse(
            String(
                KUITest::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/resourcepack_icon.yaml")!!.readAllBytes()
            )
        )).build() as GroupElement
    }

    val snake = Main()
    var off = 0f

    val animations: List<AnimationEvent<Float>> = listOf(
        AnimationEvent(
            System.currentTimeMillis(),
            1000,
            0f,
            50f,
            BackOut
        ) { off = it }.apply { onEventTrigger = { if (finished() > 1000) reset() } },
        CustomAnimationEvent {
            elem.subElement("test", GeometryLineElement::class).apply {
                while (snake.worm.cells.size != vertices.size) {
                    if (snake.worm.cells.size > vertices.size) vertices.add(Vector2f())
                    else vertices.removeLast()
                }

                for (i in 0 ..< vertices.size) {
                    vertices[i].set(
                        mouseX.toFloat() - 80 + snake.worm.cells[i]!!.x * 10 + off + 5,
                        mouseY.toFloat() - 80 + snake.worm.cells[i]!!.y * 10 + 5
                    )
                }
            }

            elem.subElement("rect_food", RectangleElement::class).apply {
                pos.set(
                    mouseX.toFloat() - 80 + snake.food.x * 10 + off,
                    mouseY.toFloat() - 80 + snake.food.y * 10
                )
            }

            elem.subElement("rect_panel", RectangleElement::class).apply {
                pos.set(
                    mouseX.toFloat() - 80 + off,
                    mouseY.toFloat() - 80
                )
            }
        },
        TimedEvent(150) { snake.step() },
        CustomAnimationEvent {
            if (InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_A)) snake.worm.crp(LEFT)
            else if (InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_D)) snake.worm.crp(RIGHT)
            else if (InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_S)) snake.worm.crp(DOWN)
            else if (InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_W)) snake.worm.crp(UP)
        }
    )

    init {
        EventLoop.objects.add(AnimatedObject(elem, animations))
    }
}
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
import kotlinx.coroutines.runBlocking
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
    var dur = 0f

    val animations: List<AnimationEvent<Float>> = listOf(
        CustomAnimationEvent { runBlocking { elem.lock.lock() } },
        CustomAnimationEvent {
            elem.subElement("test", GeometryLineElement::class).apply {
                while (snake.worm.cells.size != vertices.size) {
                    if (snake.worm.cells.size > vertices.size) vertices.add(Vector2f())
                    else vertices.removeLast()
                }

                for (i in 0 ..< vertices.size) {
                    vertices[i].set(
                        mouseX.toFloat() - 80 + snake.worm.cells[i]!!.x * 10 + 5,
                        mouseY.toFloat() - 80 + snake.worm.cells[i]!!.y * 10 + 5
                    )
                }

                vertices[0].add(when (snake.worm.currentDirection) {
                    LEFT -> Vector2f(-1f, 0f)
                    RIGHT -> Vector2f(1f, 0f)
                    DOWN -> Vector2f(0f, 1f)
                    UP -> Vector2f(0f, -1f)
                    else -> Vector2f()
                }.mul(10 * dur))

                val posn1 = vertices[vertices.size - 1]
                val posn2 = vertices[vertices.size - 2]

                posn1.set(
                    posn1.x * (1 - dur) + posn2.x * dur,
                    posn1.y * (1 - dur) + posn2.y * dur
                )
            }

            elem.subElement("rect_food", RectangleElement::class).apply {
                pos.set(
                    mouseX.toFloat() - 80 + snake.food.x * 10,
                    mouseY.toFloat() - 80 + snake.food.y * 10
                )
            }

            elem.subElement("rect_panel", RectangleElement::class).apply {
                pos.set(
                    mouseX.toFloat() - 80 - 5,
                    mouseY.toFloat() - 80 - 5
                )
            }
        },
        TimedEvent<Float>(300) { snake.step() }.apply {
            durationFetch = { dur = ExponentialOut.gen(it / 300.0).toFloat() }
        },
        CustomAnimationEvent {
            if (InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_A)) snake.worm.crp(LEFT)
            else if (InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_D)) snake.worm.crp(RIGHT)
            else if (InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_S)) snake.worm.crp(DOWN)
            else if (InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_W)) snake.worm.crp(UP)
        },
        CustomAnimationEvent { elem.lock.unlock() }
    )

    init {
        EventLoop.objects.add(AnimatedObject(elem, animations))
    }
}
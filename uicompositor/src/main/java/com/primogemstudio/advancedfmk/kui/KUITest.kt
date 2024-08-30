package com.primogemstudio.advancedfmk.kui

import com.primogemstudio.advancedfmk.kui.animation.*
import com.primogemstudio.advancedfmk.kui.elements.GeometryLineElement
import com.primogemstudio.advancedfmk.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.kui.elements.Live2DElement
import com.primogemstudio.advancedfmk.kui.elements.RectangleElement
import com.primogemstudio.advancedfmk.kui.test.snakedual.SnakeContainer
import com.primogemstudio.advancedfmk.kui.test.snakedual.Snake.Companion.DOWN
import com.primogemstudio.advancedfmk.kui.test.snakedual.Snake.Companion.LEFT
import com.primogemstudio.advancedfmk.kui.test.snakedual.Snake.Companion.RIGHT
import com.primogemstudio.advancedfmk.kui.test.snakedual.Snake.Companion.UP
import com.primogemstudio.advancedfmk.kui.yaml.YamlParser
import com.primogemstudio.advancedfmk.kui.yaml.jvm.YamlCompiler
import kotlinx.coroutines.runBlocking
import net.minecraft.client.Minecraft
import org.joml.Vector2f

val instance = KUITest()

class KUITest {
    companion object {
        var res = YamlCompiler(YamlParser.parse(
            String(
                KUITest::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/resourcepack_icon.yaml")!!.readAllBytes()
            )
        )).build() as RectangleElement
    }

    var elem = YamlCompiler(YamlParser.parse(
        String(
            KUITest::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/test.yaml")!!.readAllBytes()
        )
    )).build() as GroupElement

    val snake = SnakeContainer()
    var dur = 0f

    val animations: List<AnimationEvent<Float>> = listOf(
        CustomAnimationEvent { runBlocking { elem.renderLock.lock() } },
        CustomAnimationEvent {
            val w = Minecraft.getInstance().window.guiScaledWidth
            val h = Minecraft.getInstance().window.guiScaledHeight
            elem.subElement("test", GeometryLineElement::class).apply {
                while (snake.worm.cells.size != vertices.size) {
                    if (snake.worm.cells.size > vertices.size) vertices.add(Vector2f())
                    else vertices.removeLast()
                }

                for (i in 0 ..< vertices.size) {
                    vertices[i].set(
                        w / 2f - 80f + snake.worm.cells[i]!!.x * 10 + 5,
                        h / 2f - 80f + snake.worm.cells[i]!!.y * 10 + 5
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
                    w / 2f - 80f + snake.food.x * 10,
                    h / 2f - 80f + snake.food.y * 10
                )
            }

            elem.subElement("rect_panel", Live2DElement::class).apply {
                pos.set(
                    0f,
                    h - size.x + 30f
                )
            }

            elem.subElement("rect_panelbase", RectangleElement::class).apply {
                pos.set(
                    w / 2f - 80f - 5f,
                    h / 2f - 80f - 5f
                )
            }
        },
        TimedEvent<Float>(300) { snake.step() }.apply {
            durationFetch = { dur = it / 300f }
        },
        CustomAnimationEvent { elem.renderLock.unlock() }
    )

    init {
        EventLoop.objects.add(AnimatedObject(elem, animations))
    }
}
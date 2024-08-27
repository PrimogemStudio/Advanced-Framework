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

    val t = "0123456789abcdef"
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
        PipeAnimationEvent<Float> {
            for (x in 0 ..< 16) {
                for (y in 0 ..< 16) {
                    elem.subElement("test_rect_${t[x]}${t[y]}")?.pos?.y = it + y * 10
                }
            }
        }.apply { source = { mouseY.toFloat() - 80 } },
        PipeAnimationEvent<Float> {
            for (x in 0 ..< 16) {
                for (y in 0 ..< 16) {
                    elem.subElement("test_rect_${t[x]}${t[y]}")?.pos?.x = it + x * 10 + off
                }
            }
        }.apply { source = { mouseX.toFloat() - 80 } },
        TimedEvent(150) {
            snake.step()
            val func: (Int, Int) -> RectangleElement = { x, y -> elem.subElement("test_rect_${t[x]}${t[y]}") as RectangleElement }

            for (x in 0 ..< 16) {
                for (y in 0 ..< 16) {
                    func(x, y).color.set(1f, 1f, 1f, 0.5f)
                }
            }

            func(snake.food.x, snake.food.y).color.set(1f, 0f, 0f, 0.5f)
            snake.worm.cells.forEach {
                func(it!!.x, it.y).color.set(0f, 0f, 1f, 0.5f)
            }

            elem.subElement("test", GeometryLineElement::class).apply {
                vertices.clear()

                snake.worm.cells.forEach {
                    vertices.add(func(it!!.x, it.y).pos)
                }
            }
        },
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
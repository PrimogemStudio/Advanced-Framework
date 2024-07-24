package com.primogemstudio.advancedfmk.kui

import com.mojang.blaze3d.platform.InputConstants
import com.primogemstudio.advancedfmk.kui.animation.*
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
import org.joml.Vector4f
import org.lwjgl.glfw.GLFW
import kotlin.random.Random
import kotlin.random.nextInt

class KUITest {
    val elem = YamlCompiler(YamlParser.parse(
        String(
            KUITest::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/test.yaml")!!.readAllBytes()
        )
    )).build() as GroupElement
    val t = "0123456789abcdef"

    /*val animations: List<AnimationEvent<Float>> = listOf(
        AnimationEvent(
            System.currentTimeMillis(),
            1500,
            0f, 100f,
            BackOut
        ) {
            elem.subElement("test")?.pos?.x = it - 50
            elem.subElement("testtext")?.pos?.x = it - 50
        }.apply {
            onEventTrigger = {
                if (it.finished() > 1000) it.reset()
                it.start = mouseX.toFloat()
                it.target = mouseX.toFloat() + 100
            }
        },
        PipeAnimationEvent<Float> {
            elem.subElement("test")?.pos?.y = it
            elem.subElement("testtext")?.pos?.y = it
        }.apply { source = { mouseY.toFloat() } }
    )*/
    val snake = Main()

    val animations: List<AnimationEvent<Float>> = listOf(
        PipeAnimationEvent<Float> {
            for (x in 0 ..< 16) {
                for (y in 0 ..< 16) {
                    elem.subElement("test_rect_${t[x]}${t[y]}")?.pos?.y = it + y * 10
                }
            }
            elem.pos.y = it
        }.apply { source = { mouseY.toFloat() - 80 } },
        PipeAnimationEvent<Float> {
            for (x in 0 ..< 16) {
                for (y in 0 ..< 16) {
                    elem.subElement("test_rect_${t[x]}${t[y]}")?.pos?.x = it + x * 10
                }
            }
            elem.pos.x = it
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
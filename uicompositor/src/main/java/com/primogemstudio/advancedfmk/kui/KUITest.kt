package com.primogemstudio.advancedfmk.kui

import com.primogemstudio.advancedfmk.kui.animation.*
import com.primogemstudio.advancedfmk.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.kui.pipe.mouseX
import com.primogemstudio.advancedfmk.kui.pipe.mouseY
import com.primogemstudio.advancedfmk.kui.yaml.YamlParser
import com.primogemstudio.advancedfmk.kui.yaml.jvm.YamlCompiler

class KUITest {
    val elem = YamlCompiler(YamlParser.parse(
        String(
            KUITest::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/test.yaml")!!.readAllBytes()
        )
    )).build() as GroupElement

    val animations: List<AnimationEvent<Float>> = listOf(
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
    )

    init {
        EventLoop.objects.add(AnimatedObject(elem, animations))
    }
}
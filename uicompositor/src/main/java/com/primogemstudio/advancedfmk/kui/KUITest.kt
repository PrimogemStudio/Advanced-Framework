package com.primogemstudio.advancedfmk.kui

import com.primogemstudio.advancedfmk.kui.animation.AnimationEvent
import com.primogemstudio.advancedfmk.kui.animation.BackOut
import com.primogemstudio.advancedfmk.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.kui.yaml.YamlParser
import com.primogemstudio.advancedfmk.kui.yaml.jvm.YamlCompiler

class KUITest {
    val elem = YamlCompiler(YamlParser.parse(
        String(
            KUITest::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/test.yaml")!!.readAllBytes()
        )
    )).build() as GroupElement

    val testAnimation: AnimationEvent<Float> = AnimationEvent(
        System.currentTimeMillis(),
        1500,
        0f, 100f,
        BackOut
    ) {
        elem.subElement("test")?.pos?.x = it - 50
        elem.subElement("testtext")?.pos?.x = it - 50
    }
}
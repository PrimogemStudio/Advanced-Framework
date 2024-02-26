package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.render.uiframework.Compositor
import com.primogemstudio.advancedfmk.render.uiframework.ui.UICompound
import java.io.InputStreamReader
import java.util.stream.Collectors

fun load(): UICompound {
    return Compositor.parse(
        InputStreamReader(Compositor::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/starrail_chat.json")).readLines().stream().collect(Collectors.joining("\n"))
    )
}

fun main() {
    val a = load()
    print(a.subComponents.values.stream().toList()[0].location)
}
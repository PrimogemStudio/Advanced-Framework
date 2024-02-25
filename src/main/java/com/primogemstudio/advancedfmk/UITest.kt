package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.render.uiframework.ComposedShape
import com.primogemstudio.advancedfmk.render.uiframework.Compositor
import java.io.InputStreamReader
import java.util.function.Consumer
import java.util.stream.Collectors

fun load(): Pair<ComposedShape, Consumer<Map<String, Float>>> {
    return Compositor.compose(Compositor.parse(
        InputStreamReader(Compositor::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/starrail_chat.json")).readLines().stream().collect(Collectors.joining("\n"))
    ))
}
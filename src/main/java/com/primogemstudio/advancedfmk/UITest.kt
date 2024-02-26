package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.render.uiframework.CompositeObject
import com.primogemstudio.advancedfmk.render.uiframework.Compositor
import net.minecraft.resources.ResourceLocation
import java.io.InputStreamReader
import java.util.stream.Collectors

fun load(): Map<ResourceLocation, CompositeObject> {
    return Compositor.parse(
        InputStreamReader(Compositor::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/starrail_chat.json")).readLines().stream().collect(Collectors.joining("\n"))
    )
}
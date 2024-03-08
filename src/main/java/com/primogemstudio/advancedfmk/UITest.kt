package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.render.uiframework.Compositor
import com.primogemstudio.advancedfmk.render.uiframework.ui.UICompound
import java.io.InputStreamReader
import java.util.stream.Collectors


fun loadNew(): UICompound {
    return Compositor.parseNew(
        InputStreamReader(Compositor::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/starrail_chat_v1.json")).readLines().stream().collect(Collectors.joining("\n"))
    )
}
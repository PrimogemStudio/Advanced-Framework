package com.primogemstudio.advancedfmk.ui

import com.primogemstudio.advancedfmk.render.uiframework.Compositor
import com.primogemstudio.advancedfmk.render.uiframework.ui.UICompound


fun loadNew(): UICompound {
    return Compositor.parseNew(
        String(
            Compositor::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/starrail_chat_v1.json")!!
                .readAllBytes()
        )
    )
}
package com.primogemstudio.advancedfmk.flutter

import com.primogemstudio.advancedfmk.flutter.FlutterInstance.Companion.ITARGET
import java.util.function.Consumer

object FlutterViewEvent {
    fun resize(width: Int, height: Int) {
        FlutterEvents.instances.forEach(Consumer { i: FlutterInstance ->
            i.rect.left = (width - i.width) / 2
            i.rect.right = (width + i.width) / 2
            i.rect.top = (height - i.height) / 2
            i.rect.bottom = (height + i.height) / 2
            i.sendSizeEvent()
        })
        ITARGET.resize(width, height, false)
    }
}

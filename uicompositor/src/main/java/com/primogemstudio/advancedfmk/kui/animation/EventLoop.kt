package com.primogemstudio.advancedfmk.kui.animation

import net.minecraft.client.Minecraft

object EventLoop: Thread("AdvancedFramework-UIAnimationEvent") {
    var objects: MutableList<AnimatedObject> = mutableListOf()
    override fun run() {
        while (Minecraft.getInstance().isRunning) {
            objects.forEach { it.animationEvents.forEach { a ->
                run {
                    try {
                        a.update()
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }}
        }
    }
}
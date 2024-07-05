package com.primogemstudio.advancedfmk.render.kui.pipe

import com.mojang.blaze3d.pipeline.TextureTarget
import net.minecraft.Util
import net.minecraft.Util.OS

val IS_OSX = Util.getPlatform() === OS.OSX
val COMPOSE_FRAME = TextureTarget(1, 1, true, IS_OSX).apply {
    setClearColor(0f, 0f, 0f, 0f)
    clear(IS_OSX)
}
fun composeFrame(): TextureTarget = COMPOSE_FRAME
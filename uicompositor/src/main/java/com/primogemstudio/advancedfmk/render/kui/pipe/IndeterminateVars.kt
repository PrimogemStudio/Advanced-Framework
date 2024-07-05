package com.primogemstudio.advancedfmk.render.kui.pipe

import com.mojang.blaze3d.pipeline.TextureTarget
import net.minecraft.Util

val COMPOSE_FRAME = TextureTarget(1, 1, true, Util.getPlatform() == Util.OS.OSX)
fun composeFrame(): TextureTarget = COMPOSE_FRAME
package com.primogemstudio.advancedfmk.mmd

import com.mojang.blaze3d.platform.NativeImage
import com.primogemstudio.advancedfmk.mmd.renderer.MMDTextureAtlas
import java.io.File

object Loader {
    @JvmStatic
    fun createAtlas(ts: List<File>): MMDTextureAtlas {
        return MMDTextureAtlas(ts.map { NativeImage.read(it.inputStream()) })
    }
}
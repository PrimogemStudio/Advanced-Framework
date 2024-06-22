package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.fontengine.gen.FreeTypeFont
import net.fabricmc.api.ClientModInitializer
import java.nio.file.Files
import java.nio.file.Paths

class AdvancedFrameworkFontEngineClient : ClientModInitializer {
    override fun onInitializeClient() {
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val pth = "/home/coder2/Downloads/e22538b031e7794b26df4517a6b0c479.ttf"
    val ftfont = FreeTypeFont(Files.readAllBytes(Paths.get(pth)))
    ftfont.shape("إنه اختبار").forEach {
        print("0x${it.toHexString()} ")
    }
}
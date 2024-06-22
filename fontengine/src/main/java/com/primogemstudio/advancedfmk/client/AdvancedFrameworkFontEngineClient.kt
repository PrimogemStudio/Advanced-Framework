package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.fontengine.DefaultFont
import net.fabricmc.api.ClientModInitializer

class AdvancedFrameworkFontEngineClient : ClientModInitializer {
    override fun onInitializeClient() {
    }
}

fun main() {
    /*val pth = "/home/coder2/Downloads/e22538b031e7794b26df4517a6b0c479.ttf"
    val ftfont = FreeTypeFont(Files.readAllBytes(Paths.get(pth)))
    ftfont.shape("إنه اختبار").forEach {
        print("0x${it.toHexString()} ")
    }*/
    val fontStack = listOf(DefaultFont.FONT)
    var result: IntArray? = null
    val r = "abcdABCD!@#$%^&*()_+测试"
    fontStack.forEach { f ->
        if (result == null) result = f.shape(r)
        else {
            val temp = f.shape(r)
            for (i in temp.indices) if (result?.get(i) == 0) result?.set(i, temp[i])
        }
    }
    println(r)
    result?.toList()?.forEach { println("$it -> ${fontStack[0].getGlyphName(it)}") }
}
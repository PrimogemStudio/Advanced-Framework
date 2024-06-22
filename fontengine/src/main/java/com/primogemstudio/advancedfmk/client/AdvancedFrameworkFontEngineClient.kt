package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.fontengine.DefaultFont
import net.fabricmc.api.ClientModInitializer

class AdvancedFrameworkFontEngineClient : ClientModInitializer {
    override fun onInitializeClient() {
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    /*val pth = "/home/coder2/Downloads/e22538b031e7794b26df4517a6b0c479.ttf"
    val ftfont = FreeTypeFont(Files.readAllBytes(Paths.get(pth)))
    ftfont.shape("إنه اختبار").forEach {
        print("0x${it.toHexString()} ")
    }*/
    val fontStack = listOf(DefaultFont.FONT)
    var result: IntArray? = null
    fontStack.forEach { f ->
        if (result == null) result = f.shape("测试 abcdABCD!@#$%^&*()_+").map { f.chars[it].code }.toIntArray()
        else {
            val temp = f.shape("测试 abcdABCD!@#$%^&*()_+").map { if (it == 0) -1 else f.chars[it].code }.toIntArray()
            for (i in temp.indices) if (result?.get(i) == 0) result?.set(i, temp[i])
        }
    }
    println("测试 abcdABCD!@#$%^&*()_+")
    var r = ""
    result?.toList()?.forEach { r += it.toChar().toString() }
    println(r)
}
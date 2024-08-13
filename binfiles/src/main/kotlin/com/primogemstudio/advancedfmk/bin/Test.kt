package com.primogemstudio.advancedfmk.bin

import com.primogemstudio.advancedfmk.bin.moc3.MOC3InputStream
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val i = MOC3InputStream(Files.newInputStream(Path.of("/home/coder2/mmd/live2d/test2.moc3")))
    val y = i.parse()
    println(y)
}
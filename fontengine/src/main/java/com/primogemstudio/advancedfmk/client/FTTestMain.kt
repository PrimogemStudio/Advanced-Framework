package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeLibrary
import com.primogemstudio.advancedfmk.ftwrap.vtxf.VertexFontOutputStream
import com.primogemstudio.advancedfmk.util.Compressor
import org.apache.logging.log4j.LogManager
import java.nio.file.Files
import java.nio.file.Path

val LOGGER = LogManager.getLogger("FontGlyphViewer")

inline fun <T> timed(a: Any, func: () -> T): T {
    val start = System.currentTimeMillis()
    val t = func()
    val end = System.currentTimeMillis()
    LOGGER.info("time passed: ${end - start} ms ($a)")
    return t
}

fun main() {
    val fnt = FreeTypeFont(
        Compressor.decode(
            FreeTypeLibrary.javaClass.getResourceAsStream("/star_rail.res")!!.readAllBytes()
        )
    )

    val out = VertexFontOutputStream(Files.newOutputStream(Path.of("./StarRailFont.vtxf")), fnt)
    timed("Process ttf and write") { out.write() }
    out.close()
}
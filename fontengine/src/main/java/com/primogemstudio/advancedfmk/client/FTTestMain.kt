package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeLibrary
import com.primogemstudio.advancedfmk.ftwrap.vtxf.VertexFontInputStream
import com.primogemstudio.advancedfmk.ftwrap.vtxf.VertexFontOutputStream
import com.primogemstudio.advancedfmk.util.Compressor
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.Deflater
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

val LOGGER: Logger = LogManager.getLogger("FontGlyphViewer")

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

    File("./StarRailFont.vtxf").delete()
    val out = VertexFontOutputStream(
        GZIPOutputStream(
            Files.newOutputStream(Path.of("./StarRailFont.vtxf")), Deflater.BEST_COMPRESSION
        ), fnt
    )
    timed("Process ttf and write") { out.write() }
    out.close()

    val i = VertexFontInputStream(
        GZIPInputStream(
            Files.newInputStream(Path.of("./StarRailFont.vtxf")), Deflater.BEST_COMPRESSION
        )
    )
    i.parse()
    i.close()
}
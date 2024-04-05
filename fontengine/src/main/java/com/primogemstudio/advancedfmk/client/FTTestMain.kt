package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import com.primogemstudio.advancedfmk.ftwrap.FreeTypeLibrary
import com.primogemstudio.advancedfmk.ftwrap.vtxf.VertexFontOutputStream
import com.primogemstudio.advancedfmk.util.Compressor
import org.apache.logging.log4j.LogManager
import org.joml.Vector2f
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.DeflaterOutputStream
import java.util.zip.GZIPOutputStream
import java.util.zip.InflaterOutputStream
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants.EXIT_ON_CLOSE

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

    val out = VertexFontOutputStream(GZIPOutputStream(Files.newOutputStream(Path.of("/mnt/StarRailFont.vtxf"))), fnt)
    timed("Process ttf and write") { out.write() }
    out.close()
}
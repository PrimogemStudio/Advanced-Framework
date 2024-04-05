package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import com.primogemstudio.advancedfmk.ftwrap.vtxf.VertexFontOutputStream
import org.apache.logging.log4j.LogManager
import org.joml.Vector2f
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.GZIPOutputStream
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
    val fnt =
        FreeTypeFont(FileInputStream("/usr/share/fonts/StarRailFont.ttf").readAllBytes())

    val out = VertexFontOutputStream(Files.newOutputStream(Path.of("/mnt/StarRailFont.vtxf")), fnt)
    timed("Process ttf and write") { out.write() }
    out.close()

    /*val plist = fnt.fetchGlyphOutline('o'.code.toLong())
    val plist2 = fnt.fetchGlyphOutline('测'.code.toLong())
    val s1 = fnt.fetchGlyphBorderF('o'.code.toLong())
    val s2 = fnt.fetchGlyphBorderF('测'.code.toLong())
    timed("fetch char") { println(fnt.getAllChars()) }

    fnt.close()

    val r = timed("Split ascii char") { plist.toVertices(50).bake() }
    val r2 = timed("Split unicode char") { plist2.toVertices(50).bake() }

    val frame = JFrame()
    frame.setLocation(200, 200)
    frame.setSize(500, 500)
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.add(object : JPanel() {
        override fun paint(g: Graphics?) {
            g as Graphics2D
            val stroke = BasicStroke(0.001f)
            g.stroke = stroke
            g.color = Color.CYAN

            val sr = Vector2f(s1 * 500, 500f)
            val st = Vector2f(500f, 500f)

            g.fillRect(st.x.toInt(), st.y.toInt(), sr.x.toInt(), sr.y.toInt())

            g.color = Color.BLACK
            r.vertices.forEach {
                val a = Vector2f(sr).mul(it[0]).add(st)
                val b = Vector2f(sr).mul(it[1]).add(st)
                val c = Vector2f(sr).mul(it[2]).add(st)
                g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
            }

            val sr2 = Vector2f(s2 * 500, 500f)
            val st2 = Vector2f(0f, 0f)

            g.color = Color.CYAN
            g.fillRect(st2.x.toInt(), st2.y.toInt(), sr2.x.toInt(), sr2.y.toInt())

            g.color = Color.BLACK

            r2.vertices.forEach {
                val a = Vector2f(sr2).mul(it[0]).add(st2)
                val b = Vector2f(sr2).mul(it[1]).add(st2)
                val c = Vector2f(sr2).mul(it[2]).add(st2)
                g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
            }
        }
    })
    frame.isVisible = true*/
}
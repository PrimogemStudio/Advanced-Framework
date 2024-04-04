package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import org.joml.Vector2f
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.io.FileInputStream
import java.util.Scanner
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants.EXIT_ON_CLOSE

inline fun <T> timed(func: () -> T): T {
    val start = System.currentTimeMillis()
    val t = func()
    val end = System.currentTimeMillis()
    println("time passed: ${end - start} ms")
    return t
}

fun main() {
    val fnt =
        FreeTypeFont(FileInputStream("/usr/share/fonts/StarRailFont.ttf"))

    val oplist = fnt.fetchGlyphOutline('测'.code.toLong())
    val oplist2 = fnt.fetchGlyphOutline('j'.code.toLong())
    val s1 = fnt.fetchGlyphBorder('测'.code.toLong())
    val s2 = fnt.fetchGlyphBorder('j'.code.toLong())

    fnt.close()

    val r = oplist.split(30)
    val r2 = oplist2.split(30)

    val tri2 = r.map { it.toTriangles() }
    val tri = r2.map { it.toTriangles() }

    val frame = JFrame()
    frame.setLocation(200, 200)
    frame.setSize(500, 500)
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.add(object : JPanel() {
        override fun paint(g: Graphics?) {
            g as Graphics2D
            val stroke = BasicStroke(0.1f)
            g.stroke = stroke
            g.color = Color.CYAN

            val sr = Vector2f(s1).mul(400 / s1.y)
            val st = Vector2f(200f, 300f)

            g.fillRect(st.x.toInt(), st.y.toInt(), sr.x.toInt(), sr.y.toInt())

            g.color = Color.BLACK
            tri2.forEach { t ->
                t.forEach {
                    val a = Vector2f(sr).mul(it[0]).add(st)
                    val b = Vector2f(sr).mul(it[1]).add(st)
                    val c = Vector2f(sr).mul(it[2]).add(st)
                    g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                    g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                    g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
                }
            }

            val sr2 = Vector2f(s2).mul(400 / s2.y)
            val st2 = Vector2f(0f, 100f)

            g.color = Color.CYAN
            g.fillRect(st2.x.toInt(), st2.y.toInt(), sr2.x.toInt(), sr2.y.toInt())

            g.color = Color.BLACK
            /*r2.forEach {
                for (i in 0 until it.vertices.size) {
                    val a = Vector2f(sr2).mul(it.vertices[i]).add(st2)
                    val b = Vector2f(sr2).mul(it.vertices[(i + 1) % it.vertices.size]).add(st2)

                    g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                }
            }*/

            tri.forEach { t ->
                t.forEach {
                    val a = Vector2f(sr2).mul(it[0]).add(st2)
                    val b = Vector2f(sr2).mul(it[1]).add(st2)
                    val c = Vector2f(sr2).mul(it[2]).add(st2)
                    g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                    g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                    g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
                }
            }
        }
    })
    frame.isVisible = true
}
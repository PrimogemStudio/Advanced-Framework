package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import org.joml.Vector2f
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.io.FileInputStream
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
        FreeTypeFont(FileInputStream("G:\\Star Rail\\Game\\StarRail_Data\\StreamingAssets\\MiHoYoSDKRes\\HttpServerResources\\font\\zh-cn.ttf"))

    val oplist = fnt.fetchGlyphOutline('测'.code.toLong())
    val oplist2 = fnt.fetchGlyphOutline('j'.code.toLong())
    val s1 = fnt.fetchGlyphBorder('测'.code.toLong())
    val s2 = fnt.fetchGlyphBorder('j'.code.toLong())

    fnt.close()

    val r = oplist.split(30)
    val r2 = oplist2.split(30)

    val rb0 = r[0].toTriangles()
    val rb1 = r[1].toTriangles()
    val rb2 = r[2].toTriangles()
    val rb3 = r[3].toTriangles()
    val rb4 = r[4].toTriangles()
    val rb5 = r[5].toTriangles()
    val rb6 = r[6].toTriangles()

    val frame = JFrame()
    frame.setLocation(200, 200)
    frame.setSize(500, 500)
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.add(object : JPanel() {
        override fun paint(g: Graphics?) {
            g as Graphics2D
            val stroke = BasicStroke(0.1f)
            g.stroke = stroke
            g.color = Color.RED

            val sr = Vector2f(s1).mul(400 / s1.y)
            val st = Vector2f(200f, 300f)

            g.fillRect(st.x.toInt(), st.y.toInt(), sr.x.toInt(), sr.y.toInt())

            g.color = Color.BLACK

            rb0.forEach {
                val a = Vector2f(sr).mul(it[0]).add(st)
                val b = Vector2f(sr).mul(it[1]).add(st)
                val c = Vector2f(sr).mul(it[2]).add(st)
                g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
            }
            rb1.forEach {
                val a = Vector2f(sr).mul(it[0]).add(st)
                val b = Vector2f(sr).mul(it[1]).add(st)
                val c = Vector2f(sr).mul(it[2]).add(st)
                g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
            }
            rb2.forEach {
                val a = Vector2f(sr).mul(it[0]).add(st)
                val b = Vector2f(sr).mul(it[1]).add(st)
                val c = Vector2f(sr).mul(it[2]).add(st)
                g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
            }
            rb3.forEach {
                val a = Vector2f(sr).mul(it[0]).add(st)
                val b = Vector2f(sr).mul(it[1]).add(st)
                val c = Vector2f(sr).mul(it[2]).add(st)
                g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
            }
            rb4.forEach {
                val a = Vector2f(sr).mul(it[0]).add(st)
                val b = Vector2f(sr).mul(it[1]).add(st)
                val c = Vector2f(sr).mul(it[2]).add(st)
                g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
            }
            rb5.forEach {
                val a = Vector2f(sr).mul(it[0]).add(st)
                val b = Vector2f(sr).mul(it[1]).add(st)
                val c = Vector2f(sr).mul(it[2]).add(st)
                g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
            }
            rb6.forEach {
                val a = Vector2f(sr).mul(it[0]).add(st)
                val b = Vector2f(sr).mul(it[1]).add(st)
                val c = Vector2f(sr).mul(it[2]).add(st)
                g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                g.drawLine(a.x.toInt(), a.y.toInt(), c.x.toInt(), c.y.toInt())
                g.drawLine(c.x.toInt(), c.y.toInt(), b.x.toInt(), b.y.toInt())
            }

            val sr2 = Vector2f(s2).mul(100 / s2.y)
            val st2 = Vector2f(0f, 100f)

            g.color = Color.RED
            g.fillRect(st2.x.toInt(), st2.y.toInt(), sr2.x.toInt(), sr2.y.toInt())

            g.color = Color.BLACK
            r2.forEach {
                for (i in 0 until it.vertices.size) {
                    val a = Vector2f(sr2).mul(it.vertices[i]).add(st2)
                    val b = Vector2f(sr2).mul(it.vertices[(i + 1) % it.vertices.size]).add(st2)

                    g.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())
                }
            }
        }
    })
    frame.isVisible = true
}
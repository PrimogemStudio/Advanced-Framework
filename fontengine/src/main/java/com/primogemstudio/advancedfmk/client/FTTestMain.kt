package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import com.primogemstudio.advancedfmk.ftwrap.SVGOperation.OpType
import com.primogemstudio.advancedfmk.util.conic
import com.primogemstudio.advancedfmk.util.cubic
import org.joml.Vector2f
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
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
    val fnt = FreeTypeFont("/usr/share/fonts/StarRailFont.ttf")
    var te = 0
    /*timed {
        fnt.getAllChars().forEach {
            te += fnt.fetchGlyphOutline(it).size
        }
    }*/

    val oplist = timed { fnt.fetchGlyphOutline('我'.code.toLong()) }

    fnt.close()

    oplist.forEach {
        it.target.mul(10f)
        it.control1?.mul(10f)
        it.control2?.mul(10f)
    }

    val frame = JFrame()
    frame.setLocation(200, 200)
    frame.setSize(500, 500)
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.add(object : JPanel() {
        override fun paint(g: Graphics?) {
            g as Graphics2D
            val stroke = BasicStroke(0.1f)
            g.stroke = stroke
            g.color = Color.BLACK
            g.translate(400.0, 400.0)

            var pos = Vector2f()
            val pri = 100
            oplist.forEach {
                when (it.type) {
                    OpType.MOVE -> {}
                    OpType.LINE -> g.drawLine(pos.x.toInt(), pos.y.toInt(), it.target.x.toInt(), it.target.y.toInt())
                    OpType.CONIC -> {
                        for (i in 1 ..< pri) {
                            val t = conic(pos, it.control1!!, it.target, i.toFloat() / pri)
                            val t2 = conic(pos, it.control1, it.target, (i - 1).toFloat() / pri)
                            g.drawLine(t.x.toInt(), t.y.toInt(), t2.x.toInt(), t2.y.toInt())
                        }
                    }
                    OpType.CUBIC -> {
                        for (i in 1 ..< pri) {
                            val t = cubic(pos, it.control1!!, it.control2!!, it.target, i.toFloat() / pri)
                            val t2 = cubic(pos, it.control1, it.control2, it.target, (i - 1).toFloat() / pri)
                            g.drawLine(t.x.toInt(), t.y.toInt(), t2.x.toInt(), t2.y.toInt())
                        }
                    }
                }
                pos = Vector2f(it.target.x, it.target.y)
            }
        }
    })
    frame.isVisible = true
}
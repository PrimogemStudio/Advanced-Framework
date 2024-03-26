package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.render.uiframework.animation.*
import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

fun main() {
    val frame = JFrame()
    frame.setLocation(200, 200)
    frame.setSize(500, 500)
    frame.add(FuncPanel())
    frame.isVisible = true
}

class FuncPanel: JPanel() {
    override fun paint(g: Graphics?) {
        g?.setXORMode(Color.BLACK)
        g?.color = Color.BLACK
        g?.setPaintMode()
        g?.fillRect(0, 0, 10000, 10000)
        val points = 1000
        val xarr = IntArray(points)
        val yarr = IntArray(points)
        for (i in 0 ..< points) {
            xarr[i] = ((i / points.toDouble()) * 500.0 + 50).toInt()
            yarr[i] = 500 - (BackIn.gen(i / points.toDouble()) * 500.0).toInt() + 50
        }

        g?.color = Color.WHITE
        g?.drawPolyline(xarr, yarr, points)
    }
}
package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.ftwrap.FreeTypeFont
import com.primogemstudio.advancedfmk.ftwrap.SVGOperation
import com.primogemstudio.advancedfmk.ftwrap.SVGOperation.OpType
import com.primogemstudio.advancedfmk.util.conic
import com.primogemstudio.advancedfmk.util.cubic
import org.joml.Vector2f
import org.lwjgl.system.MemoryStack
import org.lwjgl.util.freetype.FT_Outline_Funcs
import org.lwjgl.util.freetype.FT_Vector
import org.lwjgl.util.freetype.FreeType.*
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants.EXIT_ON_CLOSE
import kotlin.math.pow

typealias Operation = SVGOperation

fun main() {
    fun i26p6tof(i: Int): Float {
        return i.toFloat() * (2.0.pow(-6).toFloat())
    }
    fun addrToVec(addr: Long): Vector2f {
        val vec = FT_Vector.create(addr)
        return Vector2f(i26p6tof(vec.x().toInt()), i26p6tof(vec.y().toInt()))
    }

    val fnt = FreeTypeFont("/usr/share/fonts/StarRailFont.ttf")
    val fce = fnt.face!!
    val oplist = mutableListOf<Operation>()

    fnt.getAllChars().forEach {
        println("0x${java.lang.Long.toHexString(it)} -> ${it.toInt().toChar()}")
    }

    val start = System.currentTimeMillis()

    val glyphIndex = FT_Get_Char_Index(fce, '我'.code.toLong())
    FT_Load_Glyph(fce, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
    val outline = fce.glyph()?.outline()!!
    val funcs = FT_Outline_Funcs.create()
        .move_to { to, _ -> oplist.add(Operation(
            type = OpType.MOVE,
            target = addrToVec(to)
        )); 0}
        .line_to { to, _ -> oplist.add(Operation(
            type = OpType.LINE,
            target = addrToVec(to)
        )); 0 }
        .conic_to { ct, to, _ -> oplist.add(Operation(
            type = OpType.CONIC,
            target = addrToVec(to),
            control1 = addrToVec(ct)
        )); 0 }
        .cubic_to { ct1, ct2, to, _ -> oplist.add(Operation(
            type = OpType.CONIC,
            target = addrToVec(to),
            control1 = addrToVec(ct1),
            control2 = addrToVec(ct2)
        )); 0 }
    FT_Outline_Decompose(outline, funcs, 1)

    val end = System.currentTimeMillis()
    println("time passed: ${end - start} ms")

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
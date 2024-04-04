package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.ftwrap.OpType
import com.primogemstudio.advancedfmk.ftwrap.Operation
import com.primogemstudio.advancedfmk.util.conic
import org.joml.Vector2f
import org.lwjgl.system.MemoryStack
import org.lwjgl.util.freetype.FT_Face
import org.lwjgl.util.freetype.FT_Matrix
import org.lwjgl.util.freetype.FT_Outline_Funcs
import org.lwjgl.util.freetype.FT_Vector
import org.lwjgl.util.freetype.FreeType.*
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.util.HexFormat
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.pow

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    fun i26p6tof(i: Int): Float {
        return i.toFloat() * (2.0.pow(-6).toFloat())
    }
    fun addrToVec(addr: Long): Vector2f {
        val ft_vec = FT_Vector.create(addr)
        return Vector2f(i26p6tof(ft_vec.x().toInt()), i26p6tof(ft_vec.y().toInt()))
    }

    val start = System.currentTimeMillis()

    var pLib: Long
    var pFace: Long
    MemoryStack.stackPush().use { stack ->
        val ptrBuff = stack.mallocPointer(1)
        FT_Init_FreeType(ptrBuff)
        pLib = ptrBuff.get(0)
        FT_New_Face(
            pLib,
            "/usr/share/fonts/StarRailFont.ttf",
            0,
            ptrBuff
        )
        pFace = ptrBuff.get(0)
    }
    val oplist = mutableListOf<Operation>()

    val multiplier = 65536L

    val fce = FT_Face.create(pFace)
    FT_Set_Pixel_Sizes(fce, 0, 24)
    FT_Set_Transform(
        fce,
        FT_Matrix.create()
            .xx(1L * multiplier)
            .xy(0L * multiplier)
            .yx(0L * multiplier)
            .yy(-1L * multiplier),

        FT_Vector.create()
            .x(0L)
            .y(0L)
    )

    MemoryStack.stackPush().use {
        var s = it.mallocInt(1)
        var l = FT_Get_First_Char(fce, s)
        while (s.get(0) != 0) {
            println("0x${java.lang.Long.toHexString(l)} -> ${l.toInt().toChar()}")
            l = FT_Get_Next_Char(fce, l, s)
        }
    }

    val glyphIndex = FT_Get_Char_Index(fce, 0x3064)
    FT_Load_Glyph(fce, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
    val outline = fce.glyph()?.outline()!!
    val funcs = FT_Outline_Funcs.create()
        .move_to { to, user -> oplist.add(Operation(
            type = OpType.MOVE,
            target = addrToVec(to)
        )); 0}
        .line_to { to, user -> oplist.add(Operation(
            type = OpType.LINE,
            target = addrToVec(to)
        )); 0 }
        .conic_to { ct, to, user -> oplist.add(Operation(
            type = OpType.CONIC,
            target = addrToVec(to),
            control1 = addrToVec(ct)
        )); 0 }
        .cubic_to { ct1, ct2, to, user -> oplist.add(Operation(
            type = OpType.CONIC,
            target = addrToVec(to),
            control1 = addrToVec(ct1),
            control2 = addrToVec(ct2)
        )); 0 }
    FT_Outline_Decompose(outline, funcs, 1)

    FT_Done_Face(fce)
    FT_Done_FreeType(pLib)

    val end = System.currentTimeMillis()
    println("time passed: ${end - start} ms")

    oplist.forEach {
        it.target.mul(10f)
        it.control1?.mul(10f)
        it.control2?.mul(10f)
    }

    val frame = JFrame()
    frame.setLocation(200, 200)
    frame.setSize(500, 500)
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
                    OpType.CUBIC -> g.drawLine(pos.x.toInt(), pos.y.toInt(), it.target.x.toInt(), it.target.y.toInt())
                    OpType.CONIC -> {
                        for (i in 1 ..< pri) {
                            val t = conic(pos, it.control1!!, it.target, i.toFloat() / pri)
                            val t2 = conic(pos, it.control1, it.target, (i - 1).toFloat() / pri)
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
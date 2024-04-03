package com.primogemstudio.advancedfmk.client

import org.lwjgl.system.MemoryStack
import org.lwjgl.util.freetype.FT_Face
import org.lwjgl.util.freetype.FT_Vector
import org.lwjgl.util.freetype.FreeType.*
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.io.File
import java.io.PrintStream
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.pow

fun main() {
    fun i26p6tof(i: Int): Float {
        return i.toFloat() * (2.0.pow(-6).toFloat())
    }

    var pLib: Long
    var pFace: Long
    MemoryStack.stackPush().use { stack ->
        val ptrBuff = stack.mallocPointer(1)
        FT_Init_FreeType(ptrBuff)
        pLib = ptrBuff.get(0)
        FT_New_Face(
            pLib,
            "G:\\Star Rail\\Game\\StarRail_Data\\StreamingAssets\\MiHoYoSDKRes\\HttpServerResources\\font\\zh-cn.ttf",
            0,
            ptrBuff
        )
        pFace = ptrBuff.get(0)
    }
    val fce = FT_Face.create(pFace)
    FT_Set_Pixel_Sizes(fce, 0, 24)
    val glyphIndex = FT_Get_Char_Index(fce, 'j'.code.toLong())
    FT_Load_Glyph(fce, glyphIndex, FT_LOAD_DEFAULT or FT_LOAD_NO_BITMAP)
    val outline = fce.glyph()?.outline()!!
    println(outline.n_points())

    val frame = JFrame()
    frame.setLocation(200, 200)
    frame.setSize(500, 500)
    frame.add(object : JPanel() {
        override fun paint(g: Graphics?) {
            g as Graphics2D
            val stroke = BasicStroke(10f);
            g.stroke = stroke
            g.color = Color.BLACK
            for (it in outline.points()) {
                g.fill(
                    Ellipse2D.Float(
                        i26p6tof(it.x().toInt()) * 10 + 100, i26p6tof(it.y().toInt()) * 10 + 200, 10f, 10f
                    )
                )
            }
        }
    })
    frame.isVisible = true

    val f = File("output.csv")
    f.delete()
    f.createNewFile()
    val ps = PrintStream(f)
    for (a in outline.points()) {
        ps.println("${a.x()},${a.y()}")
    }

    var limit = 0
    var v_last = FT_Vector.create()
    var v_control: FT_Vector
    var v_start: FT_Vector

    var point = 0
    var first = 0
    for (i in 0..<outline.n_contours()) {
        val last = outline.contours()[i].toInt()
        limit = i

        v_start = outline.points()[first]
        v_last = outline.points()[last]
        v_control = v_start

        point = first
        val tags = outline.tags()[first]
        var tag = FT_CURVE_TAG(tags.toInt())

        val fpriX = i26p6tof(v_control.x().toInt())
        val fpriY = -i26p6tof(v_control.y().toInt())

        var startX = fpriX
        var startY = fpriY

        println("$fpriX, $fpriY")
        while (point < limit) {
            point++
            limit++
            tag = FT_CURVE_TAG(tags.toInt())
            println(tag)
            when (tag) {
                FT_CURVE_TAG_ON -> {
                    val pointd = outline.points()[point]
                    val fEndX = i26p6tof(pointd.x().toInt())
                    val fEndY = -i26p6tof(pointd.y().toInt())

                    println("lineto: $startX, $startY, $fEndX, $fEndY")

                    startX = fEndX
                    startY = fEndY
                }
            }
        }

        first = last + 1
    }
}
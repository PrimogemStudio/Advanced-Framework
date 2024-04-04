package com.primogemstudio.advancedfmk.ftwrap

import org.joml.Vector2f
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Polygon(
    val vertices: List<Vector2f>
) {
    fun toTriangles(): List<Array<Vector2f>> {
        val r = mutableListOf<Array<Vector2f>>()
        val cpy = Vector(vertices)
        var s = vertices.size
        if (vertices.size <= 4) {
            r.add(vertices.toTypedArray())
            return r
        }

        var i = 0
        var j = 1
        var k = 2
        while (s > 4) {
            val a1 = cpy[j].x - cpy[i].x
            val a2 = cpy[j].y - cpy[i].y
            val b1 = cpy[k].x - cpy[i].x
            val b2 = cpy[k].y - cpy[i].y
            val cond1 = a1*b2 <= a2*b1

            var cond2 = true

            for (p in 0 ..< s) {
                if (p != i && p != j && p != k) {
                    if (inTri(cpy[i], cpy[j], cpy[k], cpy[p])) {
                        cond2 = false
                        break
                    }
                }
            }

            if (cond1 && cond2) {
                r.add(arrayOf(cpy[i], cpy[j], cpy[k]))
                cpy.removeAt(j)

                s--
                i = 0
                j = 1
                k = 2
            }
            else {
                i++
                j = i + 1
                k = j + 1
                if (k > s - 1) {
                    cpy.removeAt(j)

                    s--
                    i = 0
                    j = 1
                    k = 2
                }
            }
        }

        return r
    }
}
/*
		for (int p = 0; p < pNumbers; p++) {
			if (p != i && p != j && p != k) {/// 判断其他点是否在当前三角形内
				if (JudgeIn(MPOlygon[p].x, MPOlygon[p].y, tempx, tempy, 3 + 1)) {
					cond2 = FALSE;
					break;
				}
			}
		}
		///cond1 = true;
		if (cond1 && cond2) {/// 如果同时满足条件一和条件二
			pDC->MoveTo(MPOlygon[i]);/// 从i点到k点画线
			pDC->LineTo(MPOlygon[k]);
			for (int t = j; t < pNumbers - 1; t++) {/// 更新当前多边形
				MPOlygon[t] = MPOlygon[t + 1];
			}
			pNumbers--;/// 更新多边形的边数
			i = 0;/// 重新赋值i，j，k
			j = 1;
			k = 2;
		}
		else {/// 否则
			i ++;/// i下移一点
			j = i + 1;
			k = j + 1;
		}
	}

	pDC->SelectObject(poldPen);/// 跟新pDC

	DWORD dwStop = GetTickCount();/// 获得结束时间
	DWORD dwInterval = dwStop - dwStart;/// 获得运行时间
	CcgSXQFillPolyDoc* pDoc = (CcgSXQFillPolyDoc*)GetDocument();/// 获得Doc指针
	pDoc->polygonalTriangualtionRuntime = (double)dwInterval;/// 将数据写入Doc类中
	CString str;/// 将数据写入polygonalTriangulationInformation，并将其格式化
	str.Format(_T("%.2lf"), pDoc->polygonalTriangualtionRuntime);
	pDoc->polygonalTriangulationInformation += _T("Runtime Consuming : ") + str + _T("ms\r\n");

	pDoc->UpdateAllViews(this);/// 更新视图
	return TRUE;*/

fun inTri(a: Vector2f, b: Vector2f, c: Vector2f, p: Vector2f): Boolean = getArea(a, b, c) >= getArea(a, b, p) + getArea(a, c, p) + getArea(b, c, p)
fun getArea(a: Vector2f, b: Vector2f, c: Vector2f): Float {
    val A = getLength(a, b)
    val B = getLength(a, c)
    val C = getLength(b, c)
    val p = (A + B + C) / 2
    return sqrt(p * (p - A) * (p - B) * (p - C))
}
fun getLength(a: Vector2f, b: Vector2f): Float = sqrt(abs(b.x - a.x).pow(2) + abs(b.y - a.y).pow(2))
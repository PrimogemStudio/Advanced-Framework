package com.primogemstudio.advancedfmk.ftwrap

import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class EarCut {
    internal class Node(var i: Int, var x: Float, var y: Float) {
        var z: Float = -1f
        var steiner: Boolean = false
        var prev: Node? = null
        var next: Node? = null
        var nextZ: Node? = null
        var prevZ: Node? = null
    }

    companion object {
        fun earcut(data: FloatArray, holeIndices: IntArray?, dim: Int): List<Int> {
            val hasHoles = holeIndices != null && holeIndices.isNotEmpty()
            val outerLen = if (hasHoles) holeIndices!![0] * dim else data.size
            var outerNode = linkedList(data, 0, outerLen, dim, true)
            val triangles: MutableList<Int> = ArrayList()

            if (outerNode == null || outerNode.next === outerNode.prev) return emptyList()

            var minX = 0f
            var minY = 0f
            var maxX: Float
            var maxY: Float
            var x: Float
            var y: Float
            var invSize = 0f

            if (hasHoles) outerNode = eliminateHoles(data, holeIndices, outerNode, dim)

            if (data.size > 80 * dim) {
                maxX = data[0]
                minX = maxX
                maxY = data[1]
                minY = maxY

                var i = dim
                while (i < outerLen) {
                    x = data[i]
                    y = data[i + 1]
                    if (x < minX) minX = x
                    if (y < minY) minY = y
                    if (x > maxX) maxX = x
                    if (y > maxY) maxY = y
                    i += dim
                }

                invSize = max((maxX - minX).toDouble(), (maxY - minY).toDouble()).toFloat()
                invSize = if (invSize != 0f) (1 / invSize) else 0f
            }

            earcutLinked(outerNode, triangles, dim, minX, minY, invSize, 0)

            return triangles
        }

        private fun linkedList(data: FloatArray, start: Int, end: Int, dim: Int, clockwise: Boolean): Node? {
            var i: Int
            var last: Node? = null

            if (clockwise == (signedArea(data, start, end, dim) > 0)) {
                i = start
                while (i < end) {
                    last = insertNode(i, data[i], data[i + 1], last)
                    i += dim
                }
            } else {
                i = end - dim
                while (i >= start) {
                    last = insertNode(i, data[i], data[i + 1], last)
                    i -= dim
                }
            }

            if (last != null && equals(last, last.next)) {
                removeNode(last)
                last = last.next
            }

            return last
        }

        private fun filterPoints(start: Node?, endI: Node?): Node? {
            var end = endI
            if (start == null) return null
            if (end == null) end = start

            var p = start
            var again: Boolean
            do {
                again = false

                if (!p!!.steiner && (equals(p, p.next) || area(p.prev, p, p.next) == 0f)) {
                    removeNode(p)
                    end = p.prev
                    p = end
                    if (p === p!!.next) break
                    again = true
                } else {
                    p = p.next
                }
            } while (again || p !== end)

            return end
        }

        private fun earcutLinked(
            earI: Node?, triangles: MutableList<Int>, dim: Int, minX: Float, minY: Float, invSize: Float, pass: Int
        ) {
            var ear: Node = earI ?: return

            if (pass == 0 && invSize != 0f) indexCurve(ear, minX, minY, invSize)

            var stop = ear
            var prev: Node?
            var next: Node?

            while (ear.prev !== ear.next) {
                prev = ear.prev
                next = ear.next

                if (if (invSize != 0f) isEarHashed(ear, minX, minY, invSize) else isEar(ear)) {
                    triangles.add(prev!!.i / dim)
                    triangles.add(ear.i / dim)
                    triangles.add(next!!.i / dim)

                    removeNode(ear)

                    ear = next.next!!
                    stop = next.next!!

                    continue
                }

                ear = next!!

                if (ear === stop) {
                    when (pass) {
                        0 -> {
                            earcutLinked(filterPoints(ear, null), triangles, dim, minX, minY, invSize, 1)
                        }

                        1 -> {
                            ear = cureLocalIntersections(filterPoints(ear, null), triangles, dim)!!
                            earcutLinked(ear, triangles, dim, minX, minY, invSize, 2)
                        }

                        2 -> {
                            splitEarcut(ear, triangles, dim, minX, minY, invSize)
                        }
                    }
                    break
                }
            }
        }

        private fun isEar(ear: Node?): Boolean {
            val a = ear!!.prev
            val c = ear.next

            if (area(a, ear, c) >= 0) return false

            var p = ear.next!!.next

            while (p !== ear.prev) {
                if (pointInTriangle(a!!.x, a.y, ear.x, ear.y, c!!.x, c.y, p!!.x, p.y) && area(
                        p.prev, p, p.next
                    ) >= 0
                ) return false
                p = p.next
            }

            return true
        }

        private fun isEarHashed(ear: Node?, minX: Float, minY: Float, invSize: Float): Boolean {
            val a = ear!!.prev
            val c = ear.next

            if (area(a, ear, c) >= 0) return false

            val minTX = if (a!!.x < ear.x) (if (a.x < c!!.x) a.x else c.x) else (if (ear.x < c!!.x) ear.x else c.x)
            val minTY = if (a.y < ear.y) (if (a.y < c.y) a.y else c.y) else (if (ear.y < c.y) ear.y else c.y)
            val maxTX = if (a.x > ear.x) (if (a.x > c.x) a.x else c.x) else (if (ear.x > c.x) ear.x else c.x)
            val maxTY = if (a.y > ear.y) (if (a.y > c.y) a.y else c.y) else (if (ear.y > c.y) ear.y else c.y)

            val minZ = zOrder(minTX, minTY, minX, minY, invSize)
            val maxZ = zOrder(maxTX, maxTY, minX, minY, invSize)

            var p = ear.prevZ
            var n = ear.nextZ

            while (p != null && p.z >= minZ && n != null && n.z <= maxZ) {
                if (p !== ear.prev && p !== ear.next && pointInTriangle(
                        a.x, a.y, ear.x, ear.y, c.x, c.y, p.x, p.y
                    ) && area(p.prev, p, p.next) >= 0
                ) return false
                p = p.prevZ

                if (n !== ear.prev && n !== ear.next && pointInTriangle(
                        a.x, a.y, ear.x, ear.y, c.x, c.y, n.x, n.y
                    ) && area(n.prev, n, n.next) >= 0
                ) return false
                n = n.nextZ
            }

            while (p != null && p.z >= minZ) {
                if (p !== ear.prev && p !== ear.next && pointInTriangle(
                        a.x, a.y, ear.x, ear.y, c.x, c.y, p.x, p.y
                    ) && area(p.prev, p, p.next) >= 0
                ) return false
                p = p.prevZ
            }

            while (n != null && n.z <= maxZ) {
                if (n !== ear.prev && n !== ear.next && pointInTriangle(
                        a.x, a.y, ear.x, ear.y, c.x, c.y, n.x, n.y
                    ) && area(n.prev, n, n.next) >= 0
                ) return false
                n = n.nextZ
            }

            return true
        }

        private fun cureLocalIntersections(startI: Node?, triangles: MutableList<Int>, dim: Int): Node? {
            var start = startI
            var p = start
            do {
                val a = p!!.prev
                val b = p.next!!.next

                if (!equals(a, b) && intersects(a, p, p.next, b) && locallyInside(a, b) && locallyInside(b, a)) {
                    triangles.add(a!!.i / dim)
                    triangles.add(p.i / dim)
                    triangles.add(b!!.i / dim)

                    removeNode(p)
                    removeNode(p.next)

                    start = b
                    p = start
                }
                p = p.next
            } while (p !== start)

            return filterPoints(p, null)
        }

        private fun splitEarcut(
            start: Node?, triangles: MutableList<Int>, dim: Int, minX: Float, minY: Float, invSize: Float
        ) {
            var a = start
            do {
                var b = a!!.next!!.next
                while (b !== a!!.prev) {
                    if (a!!.i != b!!.i && isValidDiagonal(a, b)) {
                        var c: Node? = splitPolygon(a, b)
                        a = filterPoints(a, a.next)
                        c = filterPoints(c, c!!.next)
                        earcutLinked(a, triangles, dim, minX, minY, invSize, 0)
                        earcutLinked(c, triangles, dim, minX, minY, invSize, 0)
                        return
                    }
                    b = b.next
                }
                a = a!!.next
            } while (a !== start)
        }

        private fun eliminateHoles(data: FloatArray, holeIndices: IntArray?, outerNodeI: Node, dim: Int): Node {
            var outerNode: Node? = outerNodeI
            val queue: MutableList<Node?> = ArrayList()
            var start: Int
            var end: Int
            var list: Node?

            var i = 0
            val len = holeIndices!!.size
            while (i < len) {
                start = holeIndices[i] * dim
                end = if (i < len - 1) holeIndices[i + 1] * dim else data.size
                list = linkedList(data, start, end, dim, false)
                if (list === list!!.next) list!!.steiner = true
                queue.add(getLeftmost(list))
                i++
            }

            Collections.sort(queue, compareX())

            i = 0
            while (i < queue.size) {
                eliminateHole(queue[i], outerNode)
                outerNode = filterPoints(outerNode, outerNode!!.next)
                i++
            }

            return outerNode!!
        }

        private fun compareX(): Comparator<in Node?> {
            return Comparator { a, b -> a!!.x.compareTo(b!!.x) }
        }

        private fun eliminateHole(hole: Node?, outerNodeI: Node?) {
            var outerNode = outerNodeI
            outerNode = findHoleBridge(hole, outerNode)
            if (outerNode != null) {
                val b = splitPolygon(outerNode, hole)

                filterPoints(outerNode, outerNode.next)
                filterPoints(b, b.next)
            }
        }

        private fun findHoleBridge(hole: Node?, outerNode: Node?): Node? {
            var p = outerNode
            val hx = hole!!.x
            val hy = hole.y
            var qx = -Float.MAX_VALUE
            var m: Node? = null

            do {
                if (hy <= p!!.y && hy >= p.next!!.y && p.next!!.y != p.y) {
                    val x = p.x + (hy - p.y) * (p.next!!.x - p.x) / (p.next!!.y - p.y)
                    if (x <= hx && x > qx) {
                        qx = x
                        if (x == hx) {
                            if (hy == p.y) return p
                            if (hy == p.next!!.y) return p.next
                        }
                        m = if (p.x < p.next!!.x) p else p.next
                    }
                }
                p = p.next
            } while (p !== outerNode)

            if (m == null) return null

            if (hx == qx) return m

            val stop: Node = m
            val mx = m.x
            val my = m.y
            var tanMin = Float.MAX_VALUE
            var tan: Float

            p = m

            do {
                if (hx >= p!!.x && p.x >= mx && hx != p.x && pointInTriangle(
                        if (hy < my) hx else qx, hy, mx, my, if (hy < my) qx else hx, hy, p.x, p.y
                    )
                ) {
                    tan = (abs((hy - p.y).toDouble()) / (hx - p.x)).toFloat()

                    if (locallyInside(
                            p, hole
                        ) && (tan < tanMin || (tan == tanMin && (p.x > m!!.x || (p.x == m.x && sectorContainsSector(
                            m, p
                        )))))
                    ) {
                        m = p
                        tanMin = tan
                    }
                }

                p = p.next
            } while (p !== stop)

            return m
        }

        private fun sectorContainsSector(m: Node?, p: Node?): Boolean {
            return area(m!!.prev, m, p!!.prev) < 0 && area(p.next, m, m.next) < 0
        }

        private fun indexCurve(start: Node, minX: Float, minY: Float, invSize: Float) {
            var p: Node? = start
            do {
                if (p!!.z == -1f) p.z = zOrder(p.x, p.y, minX, minY, invSize)
                p.prevZ = p.prev
                p.nextZ = p.next
                p = p.next
            } while (p !== start)

            p.prevZ!!.nextZ = null
            p.prevZ = null

            sortLinked(p)
        }

        private fun sortLinked(listI: Node?): Node? {
            var list = listI
            var i: Int
            var p: Node?
            var q: Node?
            var e: Node?
            var tail: Node?
            var numMerges: Int
            var pSize: Int
            var qSize: Int
            var inSize = 1

            do {
                p = list
                list = null
                tail = null
                numMerges = 0

                while (p != null) {
                    numMerges++
                    q = p
                    pSize = 0
                    i = 0
                    while (i < inSize) {
                        pSize++
                        q = q!!.nextZ
                        if (q == null) break
                        i++
                    }
                    qSize = inSize

                    while (pSize > 0 || (qSize > 0 && q != null)) {
                        if (pSize != 0 && (qSize == 0 || q == null || p!!.z <= q.z)) {
                            e = p
                            p = p!!.nextZ
                            pSize--
                        } else {
                            e = q
                            q = q!!.nextZ
                            qSize--
                        }

                        if (tail != null) tail.nextZ = e
                        else list = e

                        e!!.prevZ = tail
                        tail = e
                    }

                    p = q
                }

                tail!!.nextZ = null
                inSize *= 2
            } while (numMerges > 1)

            return list
        }

        private fun zOrder(x0: Float, y0: Float, minX: Float, minY: Float, invSize: Float): Float {
            var x = (32767 * (x0 - minX) * invSize).toInt()
            var y = (32767 * (y0 - minY) * invSize).toInt()

            x = (x or (x shl 8)) and 0x00FF00FF
            x = (x or (x shl 4)) and 0x0F0F0F0F
            x = (x or (x shl 2)) and 0x33333333
            x = (x or (x shl 1)) and 0x55555555

            y = (y or (y shl 8)) and 0x00FF00FF
            y = (y or (y shl 4)) and 0x0F0F0F0F
            y = (y or (y shl 2)) and 0x33333333
            y = (y or (y shl 1)) and 0x55555555

            return (x or (y shl 1)).toFloat()
        }

        private fun getLeftmost(start: Node?): Node? {
            var p = start
            var leftmost = start
            do {
                if (p!!.x < leftmost!!.x || (p.x == leftmost.x && p.y < leftmost.y)) leftmost = p
                p = p.next
            } while (p !== start)

            return leftmost
        }

        private fun pointInTriangle(
            ax: Float, ay: Float, bx: Float, by: Float, cx: Float, cy: Float, px: Float, py: Float
        ): Boolean {
            return (cx - px) * (ay - py) - (ax - px) * (cy - py) >= 0 && ((ax - px) * (by - py) - (bx - px) * (ay - py) >= 0) && ((bx - px) * (cy - py) - (cx - px) * (by - py) >= 0)
        }

        private fun isValidDiagonal(a: Node?, b: Node?): Boolean {
            return a!!.next!!.i != b!!.i && a.prev!!.i != b.i && !intersectsPolygon(a, b) && (locallyInside(
                a, b
            ) && locallyInside(b, a) && middleInside(a, b) && (area(a.prev, a, b.prev) != 0f || area(
                a, b.prev, b
            ) != 0f) || equals(a, b) && area(a.prev, a, a.next) > 0 && area(b.prev, b, b.next) > 0)
        }

        private fun area(p: Node?, q: Node?, r: Node?): Float {
            return (q!!.y - p!!.y) * (r!!.x - q.x) - (q.x - p.x) * (r.y - q.y)
        }

        private fun equals(p1: Node?, p2: Node?): Boolean {
            return p1!!.x == p2!!.x && p1.y == p2.y
        }

        private fun intersects(p1: Node?, q1: Node?, p2: Node?, q2: Node?): Boolean {
            val o1 = sign(area(p1, q1, p2))
            val o2 = sign(area(p1, q1, q2))
            val o3 = sign(area(p2, q2, p1))
            val o4 = sign(area(p2, q2, q1))

            if (o1 != o2 && o3 != o4) return true


            if (o1 == 0 && onSegment(p1, p2, q1)) return true

            if (o2 == 0 && onSegment(p1, q2, q1)) return true

            if (o3 == 0 && onSegment(p2, p1, q2)) return true

            if (o4 == 0 && onSegment(p2, q1, q2)) return true


            return false
        }

        private fun onSegment(p: Node?, q: Node?, r: Node?): Boolean {
            return q!!.x <= max(
                p!!.x.toDouble(), r!!.x.toDouble()
            ) && q.x >= min(p.x.toDouble(), r.x.toDouble()) && q.y <= max(
                p.y.toDouble(), r.y.toDouble()
            ) && q.y >= min(p.y.toDouble(), r.y.toDouble())
        }

        private fun sign(num: Float): Int {
            return if (num > 0) 1 else if (num < 0) -1 else 0
        }

        private fun intersectsPolygon(a: Node?, b: Node?): Boolean {
            var p = a
            do {
                if (p!!.i != a!!.i && p.next!!.i != a.i && p.i != b!!.i && p.next!!.i != b.i && intersects(
                        p, p.next, a, b
                    )
                ) return true
                p = p.next
            } while (p !== a)

            return false
        }

        private fun locallyInside(a: Node?, b: Node?): Boolean {
            return if (area(a!!.prev, a, a.next) < 0) area(a, b, a.next) >= 0 && area(a, a.prev, b) >= 0 else area(
                a, b, a.prev
            ) < 0 || area(a, a.next, b) < 0
        }

        private fun middleInside(a: Node?, b: Node?): Boolean {
            var p = a
            var inside = false
            val px = (a!!.x + b!!.x) / 2
            val py = (a.y + b.y) / 2
            do {
                if (((p!!.y > py) != (p.next!!.y > py)) && (p.next!!.y != p.y) && (px < (p.next!!.x - p.x) * (py - p.y) / (p.next!!.y - p.y) + p.x)) inside =
                    !inside
                p = p.next
            } while (p !== a)

            return inside
        }

        private fun splitPolygon(a: Node?, b: Node?): Node {
            val a2 = Node(a!!.i, a.x, a.y)
            val b2 = Node(b!!.i, b.x, b.y)
            val an = a.next
            val bp = b.prev

            a.next = b
            b.prev = a

            a2.next = an
            an!!.prev = a2

            b2.next = a2
            a2.prev = b2

            bp!!.next = b2
            b2.prev = bp

            return b2
        }

        private fun insertNode(i: Int, x: Float, y: Float, last: Node?): Node {
            val p = Node(i, x, y)

            if (last == null) {
                p.prev = p
                p.next = p
            } else {
                p.next = last.next
                p.prev = last
                last.next!!.prev = p
                last.next = p
            }
            return p
        }

        private fun removeNode(p: Node?) {
            p!!.next!!.prev = p.prev
            p.prev!!.next = p.next

            if (p.prevZ != null) p.prevZ!!.nextZ = p.nextZ
            if (p.nextZ != null) p.nextZ!!.prevZ = p.prevZ
        }

        private fun signedArea(data: FloatArray, start: Int, end: Int, dim: Int): Float {
            var sum = 0f
            var i = start
            var j = end - dim
            while (i < end) {
                sum += (data[j] - data[i]) * (data[i + 1] + data[j + 1])
                j = i
                i += dim
            }
            return sum
        }
    }
}
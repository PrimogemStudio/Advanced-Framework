package com.primogemstudio.advancedfmk.kui.test.snakedual

import org.joml.Vector2i

class Snake {
    var cells: Array<Vector2i?>

    var currentDirection: Int
        private set

    init {
        cells = arrayOfNulls(DEFAULT_LENGTH)
        for (i in cells.indices) {
            cells[i] = Vector2i(i, 0) // [0,0] [1,0] [2,0]
        }
        currentDirection = DOWN
    }

    fun contains(x: Int, y: Int): Boolean {
        for (i in cells.indices) {
            val Vector2i = cells[i]
            if (Vector2i!!.x == x && Vector2i.y == y) {
                return true
            }
        }
        return false
    }

    fun crp(direction: Int) {
        if (currentDirection + direction == 0) return
        currentDirection = direction
    }

    fun creep(direction: Int, food: Vector2i): Boolean {
        if (currentDirection + direction == 0) {
            return false
        }
        currentDirection = direction
        val head = createHead(direction)
        val eat = head.x == food.x && head.y == food.y
        if (eat) {
            cells = cells.copyOf(cells.size + 1)
        }
        for (i in cells.size - 1 downTo 1) {
            cells[i] = cells[i - 1]
        }
        cells[0] = head
        return eat
    }

    @JvmOverloads
    fun hit(direction: Int = currentDirection): Boolean {
        if (currentDirection + direction == 0) {
            return false
        }
        val head = createHead(direction)
        if (head.x < 0 || head.x >= SnakeContainer.COLS || head.y < 0 || head.y >= SnakeContainer.ROWS) {
            return true
        }
        for (i in 0 until cells.size - 1) {
            val vector2i = cells[i]
            if (vector2i!!.x == head.x && vector2i.y == head.y) {
                return true
            }
        }
        return false
    }

    fun creep(food: Vector2i): Boolean {
        return creep(currentDirection, food)
    }

    private fun createHead(direction: Int): Vector2i {
        var x = cells[0]!!.x
        var y = cells[0]!!.y
        when (direction) {
            DOWN -> y++
            UP -> y--
            LEFT -> x--
            RIGHT -> x++
        }
        return Vector2i(x, y)
    }

    override fun toString(): String {
        return cells.contentToString()
    }

    companion object {
        const val DEFAULT_LENGTH: Int = 3
        const val UP: Int = 1
        const val DOWN: Int = -1
        const val LEFT: Int = 2
        const val RIGHT: Int = -2
    }
}
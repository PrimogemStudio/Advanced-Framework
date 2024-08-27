package com.primogemstudio.advancedfmk.kui.test.snakedual

class Move {
    var cells: Array<Node?>

    var currentDirection: Int
        private set

    init {
        cells = arrayOfNulls(DEFAULT_LENGTH)
        for (i in cells.indices) {
            cells[i] = Node(i, 0) // [0,0] [1,0] [2,0]
        }
        currentDirection = DOWN
    }

    fun contains(x: Int, y: Int): Boolean {
        for (i in cells.indices) {
            val node = cells[i]
            if (node!!.x == x && node.y == y) {
                return true
            }
        }
        return false
    }

    fun crp(direction: Int) {
        if (currentDirection + direction == 0) return
        currentDirection = direction
    }

    fun creep(direction: Int, food: Node): Boolean {
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
        if (head.x < 0 || head.x >= Main.COLS || head.y < 0 || head.y >= Main.ROWS) {
            return true
        }
        for (i in 0 until cells.size - 1) {
            val node = cells[i]
            if (node!!.x == head.x && node.y == head.y) {
                return true
            }
        }
        return false
    }

    fun creep(food: Node): Boolean {
        return creep(currentDirection, food)
    }

    private fun createHead(direction: Int): Node {
        var x = cells[0]!!.x
        var y = cells[0]!!.y
        when (direction) {
            DOWN -> y++
            UP -> y--
            LEFT -> x--
            RIGHT -> x++
        }
        return Node(x, y)
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
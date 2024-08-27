package com.primogemstudio.advancedfmk.kui.test.snakedual

import com.primogemstudio.advancedfmk.kui.test.snakedual.Move.Companion.LEFT
import com.primogemstudio.advancedfmk.kui.test.snakedual.Move.Companion.RIGHT
import com.primogemstudio.advancedfmk.kui.test.snakedual.Move.Companion.UP
import com.primogemstudio.advancedfmk.kui.test.snakedual.Move.Companion.DOWN
import java.util.*

class Main {
    var worm: Move
    var food: Node

    init {
        worm = Move()
        food = createFood()
    }

    private fun createFood(): Node {
        var x: Int
        var y: Int
        val r = Random()
        do {
            x = r.nextInt(COLS)
            y = r.nextInt(ROWS)
        } while (worm.contains(x, y))
        return Node(x, y)
    }

    fun step() {
        if (worm.hit()) {
            worm = Move()
            food = createFood()
        } else {
            if (worm.creep(food)) food = createFood()
        }
    }

    companion object {
        const val ROWS: Int = 16
        const val COLS: Int = 16
    }
}
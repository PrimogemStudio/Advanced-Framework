package com.primogemstudio.advancedfmk.kui.test.snakedual

import org.joml.Vector2i
import java.util.*

class Main {
    var worm: Move
    var food: Vector2i

    init {
        worm = Move()
        food = createFood()
    }

    private fun createFood(): Vector2i {
        var x: Int
        var y: Int
        val r = Random()
        do {
            x = r.nextInt(COLS)
            y = r.nextInt(ROWS)
        } while (worm.contains(x, y))
        return Vector2i(x, y)
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
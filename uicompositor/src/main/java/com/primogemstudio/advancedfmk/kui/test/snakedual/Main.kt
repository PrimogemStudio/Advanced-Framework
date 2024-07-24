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

    override fun toString(): String {
        return "worm:$worm\nfood:$food"
    }

    fun step() {
        if (worm.hit()) {
            worm = Move()
            food = createFood()
        } else {
            if (worm.creep(food)) food = createFood()
        }
    }

    private fun creepTo(direction: Int) {
        if (worm.hit(direction)) {
            worm = Move()
            food = createFood()
        }
        else if (worm.creep(direction, food)) food = createFood()
    }

    companion object {
        const val ROWS: Int = 16
        const val COLS: Int = 16

        @JvmStatic
        fun main(args: Array<String>) {
            val r = Main()
            val sc = Scanner(System.`in`)

            while (true) {
                when (sc.nextLine()) {
                    "a" -> r.creepTo(LEFT)
                    "d" -> r.creepTo(RIGHT)
                    "w" -> r.creepTo(UP)
                    "s" -> r.creepTo(DOWN)
                }

                val ls = Array(16) { IntArray(16) { 0 } }

                ls[r.food.y][r.food.x] = -1
                for (index in r.worm.cells.indices) {
                    val cell = r.worm.cells[index]
                    ls[cell!!.y][cell.x] = index + 1
                }

                ls.forEach {
                    it.forEach { i ->
                        print("$i ")
                    }
                    println("")
                }
                println("*".repeat(20))
            }
        }
    }
}
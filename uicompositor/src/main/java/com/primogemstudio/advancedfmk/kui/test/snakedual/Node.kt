package com.primogemstudio.advancedfmk.kui.test.snakedual

data class Node(
    var x: Int = 0,
    var y: Int = 0
) {
    override fun toString(): String {
        return "[$x,$y]"
    }
}
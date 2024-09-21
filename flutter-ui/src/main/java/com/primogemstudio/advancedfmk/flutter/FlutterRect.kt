package com.primogemstudio.advancedfmk.flutter

class FlutterRect {
    var left: Int = 0
    var top: Int = 0
    var right: Int = 0
    var bottom: Int = 0

    constructor(left: Int, top: Int, right: Int, bottom: Int) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    constructor()

    override fun toString(): String {
        return "$left $top $right $bottom"
    }
}

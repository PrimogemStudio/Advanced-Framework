package com.primogemstudio.advancedfmk.render

enum class ColorComponent {
    R, G, B, A
}
fun Int.colorFetchM(cc: ColorComponent): Float {
    return this.shr(when(cc) {
        ColorComponent.A -> 24
        ColorComponent.R -> 16
        ColorComponent.G -> 8
        ColorComponent.B -> 0
    }).and(0xFF) / 255f
}
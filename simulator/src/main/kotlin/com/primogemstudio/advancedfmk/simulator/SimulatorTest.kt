package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.objects.RoundtripCharacterImplv0

fun main() {
    val chr = RoundtripCharacterImplv0(100f, 25f)
    println(chr.getRawData())
}
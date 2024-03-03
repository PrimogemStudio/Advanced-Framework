package com.primogemstudio.advancedfmk.interfaces

interface BufferBuilderExt {
    fun fullFormat(): Boolean
    fun bumpNxt(d: Int)
    fun submit()
    fun resize()
    fun padding(): Int
    fun setPtr(ptr: Int)
}
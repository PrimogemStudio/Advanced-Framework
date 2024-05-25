package com.primogemstudio.advancedfmk.interfaces

import com.primogemstudio.advancedfmk.mmd.PMXModel

interface BufferBuilderExt {
    fun fullFormat(): Boolean
    fun bumpNxt(d: Int)
    fun submit()
    fun resize()
    fun padding(): Int
    fun setPtr(ptr: Int)
    fun getPMXModel(): PMXModel?
    fun setPMXModel(model: PMXModel?)
}

interface RenderedBufferExt {
    fun getPMXModel(): PMXModel?
    fun setPMXModel(model: PMXModel?)
}
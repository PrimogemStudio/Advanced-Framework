package com.primogemstudio.advancedfmk.interfaces

import com.primogemstudio.advancedfmk.mmd.PMXModel

interface BufferBuilderExt {
    fun fullFormat(): Boolean
    fun resize(size: Int): Long
    fun padding(): Int
    fun getPMXModel(): PMXModel?
    fun setPMXModel(model: PMXModel?)
}

interface DrawStateExt {
    fun getPMXModel(): PMXModel?
    fun setPMXModel(model: PMXModel?)
}
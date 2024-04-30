package com.primogemstudio.advancedfmk.simulator.objects

interface MappedObject {
    fun getRawData(): Map<String, Any>
    fun overrideData(v: Map<String, Any>)
}
package com.primogemstudio.advancedfmk.simulator.objects.interfaces

interface MappedObject {
    var rawData: Map<String, Any>

    val staticData: Map<String, Any>
}
package com.primogemstudio.advancedfmk.simulator

data class ResultWrapper(
    val finished: Boolean,
    val attr: Map<String, Any> = mutableMapOf()
)

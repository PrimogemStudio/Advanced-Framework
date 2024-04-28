package com.primogemstudio.advancedfmk.simulator.roundtrip

data class OperationDataWrapper(
    val from: CharacterBase?,
    val targets: MutableMap<CharacterBase, Float> = mutableMapOf()
)

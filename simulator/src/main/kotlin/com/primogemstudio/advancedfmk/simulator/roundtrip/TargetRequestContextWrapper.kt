package com.primogemstudio.advancedfmk.simulator.roundtrip

import com.primogemstudio.advancedfmk.simulator.ContextWrapper

class TargetRequestContextWrapper(
    override val simulator: SimulatedUniverse,
    val `object`: CharacterBase,
    val targetObjects: List<CharacterBase>
): ContextWrapper(simulator)
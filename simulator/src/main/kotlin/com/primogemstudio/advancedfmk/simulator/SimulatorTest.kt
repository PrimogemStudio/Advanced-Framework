package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.roundtrip.CharacterBase
import com.primogemstudio.advancedfmk.simulator.roundtrip.DefaultedObject
import com.primogemstudio.advancedfmk.simulator.roundtrip.SimulatedUniverse

class SnapshotTree(
    val parent: SnapshotTree?
): HashMap<Simulator, SnapshotTree>()

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val simu = SimulatedUniverse()
    simu.characters.add(DefaultedObject(
        "Test character 1", 100f, 25f, CharacterBase.Type.Controllable
    ))
    simu.characters.add(DefaultedObject(
        "Test character 2", 200f, 15f, CharacterBase.Type.Controllable
    ))
    simu.characters.add(DefaultedObject(
        "Test character 3", 50f, 50f, CharacterBase.Type.Controllable
    ))

    simu.enemies.add(DefaultedObject(
        "Test enemy 1", 50f, 20f, CharacterBase.Type.UnControllable
    ))
    simu.enemies.add(DefaultedObject(
        "Test enemy 2", 75f, 20f, CharacterBase.Type.UnControllable
    ))


    val snap = SnapshotTree(null)
    var current: SnapshotTree
    snap[simu.clone()] = SnapshotTree(snap).apply { current = this }
    val sig = { current[simu.clone()] = SnapshotTree(current).apply { current = this } }
    while (!simu.func(simu.simulateStep()).finished) sig()
    sig()

    with(simu.operationStack) {
        var i = 0
        while (!isEmpty()) {
            val r = pop()
            println("-0x${i.toHexString()} ${r.from} -> ${r.targets.map { it.key }} (${r.targets.map { it.value }}))")
            i++
        }
    }
}
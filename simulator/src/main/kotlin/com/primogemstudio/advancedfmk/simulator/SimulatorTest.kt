package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.roundtrip.CharacterBase
import com.primogemstudio.advancedfmk.simulator.roundtrip.DefaultedObject
import com.primogemstudio.advancedfmk.simulator.roundtrip.SimulatedUniverse

class SnapshotTree: HashMap<SimulatedUniverse, SnapshotTree>()

fun genTarget(simu: SimulatedUniverse, depth: Int = 0): SnapshotTree {
    val target = SnapshotTree()
    target[simu] = SnapshotTree()
    if (simu.func(simu.genContext()).finished || depth > 2) return target

    println("${simu.getCurrentChar().getName()} $depth")
    for (i in 0 ..< simu.getCurrentChar().getChoicesCount()) {
        val cpy = simu.clone()
        cpy.getCurrentChar().currentChoice = i
        cpy.simulateStep()
        target[simu]?.set(cpy, genTarget(cpy, depth + 1))
    }
    return target
}

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val simu = SimulatedUniverse()
    simu.characters.add(DefaultedObject(
        "Test character 1", 100f, 25f, CharacterBase.Type.Controllable, simu
    ))
    simu.characters.add(DefaultedObject(
        "Test character 2", 200f, 15f, CharacterBase.Type.Controllable, simu
    ))
    simu.characters.add(DefaultedObject(
        "Test character 3", 50f, 50f, CharacterBase.Type.Controllable, simu
    ))

    simu.enemies.add(DefaultedObject(
        "Test enemy 1", 50f, 20f, CharacterBase.Type.UnControllable, simu
    ))
    simu.enemies.add(DefaultedObject(
        "Test enemy 2", 75f, 20f, CharacterBase.Type.UnControllable, simu
    ))

    /*val t = simu.clone()
    runBlocking { t.loopMain().await() }

    with(t.operationStack) {
        var i = 0
        while (!isEmpty()) {
            val r = pop()
            println("-0x${i.toHexString()} ${r.from} -> ${r.targets.map { it.key }} (${r.targets.map { it.value }}))")
            i++
        }
    }
    println("Test passed!")*/
    val t = genTarget(simu)
    println(t)
}
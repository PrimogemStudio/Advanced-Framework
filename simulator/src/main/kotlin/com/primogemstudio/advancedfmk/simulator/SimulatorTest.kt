package com.primogemstudio.advancedfmk.simulator

import com.primogemstudio.advancedfmk.simulator.roundtrip.CharacterBase
import com.primogemstudio.advancedfmk.simulator.roundtrip.DefaultedObject
import com.primogemstudio.advancedfmk.simulator.roundtrip.SimulatedUniverse
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val simu = SimulatedUniverse(funcRequestTarget = {
        var i = -1
        while (i == -1 || !it.targetObjects[i].alive()) {
            i = Random.nextInt(0, it.targetObjects.size)
        }
        intArrayOf(i)
    }, funcUncontrolledRequestTarget = {
        var i = -1
        while (i == -1 || !it.targetObjects[i].alive()) {
            i = Random.nextInt(0, it.targetObjects.size)
        }
        intArrayOf(i)
    })

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
        "Test enemy 2", 75f, 5f, CharacterBase.Type.UnControllable
    ))

    runBlocking { simu.loopMain().await() }

    with(simu.operationStack) {
        var i = 0
        while (!isEmpty()) {
            val r = pop()
            println("-0x${i.toHexString()} ${r.from} -> ${r.targets.map { it.key }} (${r.targets.map { it.value }}))")
            i++
        }
    }
}
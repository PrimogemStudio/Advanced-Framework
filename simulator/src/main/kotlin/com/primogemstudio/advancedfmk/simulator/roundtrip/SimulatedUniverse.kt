package com.primogemstudio.advancedfmk.simulator.roundtrip

import com.primogemstudio.advancedfmk.simulator.ContextWrapper
import com.primogemstudio.advancedfmk.simulator.ResultWrapper
import com.primogemstudio.advancedfmk.simulator.Simulator
import java.util.*

class SimulatedUniverse(
    val characters: MutableList<CharacterBase> = mutableListOf(),
    val enemies: MutableList<CharacterBase> = mutableListOf(),
    val funcRequestTarget: (TargetRequestContextWrapper) -> IntArray,
    val funcUncontrolledRequestTarget: (TargetRequestContextWrapper) -> IntArray
): Simulator({
    enemies.flatMap { if (!it.alive()) listOf(it) else listOf() }
        .forEach { enemies.remove(it) }
    ResultWrapper(
        characters.map { it.calcHealth() }.sum() == 0f ||
                enemies.map { it.calcHealth() }.sum() == 0f
    )
}) {
    private val operateQueue: Deque<MutableList<CharacterBase>> = LinkedList()
    val operationStack: Stack<OperationDataWrapper> = Stack()
    override fun simulateStep(context: ContextWrapper) {
        if (operateQueue.isEmpty()) {
            characters.filter { it.alive() }.forEach { operateQueue.offer(mutableListOf(it)) }
            enemies.forEach { operateQueue.offer(mutableListOf(it)) }
        }

        val cu = operateQueue.poll()
        while (cu.size > 0) {
            val current = cu[0]
            run {
                if (!current.alive()) return@run
                val result = OperationDataWrapper(current)
                when (current.type()) {
                    CharacterBase.Type.Controllable -> enemies
                    CharacterBase.Type.UnControllable -> characters
                    CharacterBase.Type.UnControllableUnrequired -> characters
                }.apply {
                    (if (this == characters) funcUncontrolledRequestTarget else funcRequestTarget)(
                        TargetRequestContextWrapper(
                            this@SimulatedUniverse, current, this
                        )
                    ).map { this[it] }.map { t ->
                        Pair(t, current.calcOutputMain().apply {
                            t.operateHealth { it.setter.call(it.getter.call() - this) }
                        })
                    }.forEach { (char, data) -> result.targets[char] = data }
                    operationStack.push(result)
                }
            }
            cu.removeAt(0)
        }
    }

    public override fun clone(): SimulatedUniverse =  SimulatedUniverse(
            characters.map { it.clone() }.toMutableList(),
            enemies.map { it.clone() }.toMutableList(),
            funcRequestTarget,
            funcUncontrolledRequestTarget
        ).apply {
            this@apply.operationStack.addAll(this@SimulatedUniverse.operationStack)
            this@apply.operateQueue.addAll(this@SimulatedUniverse.operateQueue)
        }
}
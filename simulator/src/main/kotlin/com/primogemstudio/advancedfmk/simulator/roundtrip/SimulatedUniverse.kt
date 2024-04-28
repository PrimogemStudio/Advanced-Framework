package com.primogemstudio.advancedfmk.simulator.roundtrip

import com.primogemstudio.advancedfmk.simulator.ContextWrapper
import com.primogemstudio.advancedfmk.simulator.ResultWrapper
import com.primogemstudio.advancedfmk.simulator.Simulator
import java.util.*

class SimulatedUniverse(
    val characters: MutableList<CharacterBase> = mutableListOf(),
    val enemies: MutableList<CharacterBase> = mutableListOf()
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

        val cu = operateQueue.peek()
        val current = cu[0]
        run {
            if (!current.alive()) return@run
            val result = OperationDataWrapper(current)
            when (current.type()) {
                CharacterBase.Type.Controllable -> enemies
                CharacterBase.Type.UnControllable -> characters
                CharacterBase.Type.UnControllableUnrequired -> characters
            }.apply {
                current.selectTargets(
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
        if (cu.size <= 0) operateQueue.poll()
    }

    public override fun clone(): SimulatedUniverse =  SimulatedUniverse(
            characters.map { it.clone() }.toMutableList(),
            enemies.map { it.clone() }.toMutableList()
        ).apply {
            this@apply.operationStack.addAll(this@SimulatedUniverse.operationStack)
            this@apply.operateQueue.addAll(this@SimulatedUniverse.operateQueue)
            this@apply.characters.map { it.simulator = this@apply }
            this@apply.enemies.map { it.simulator = this@apply }
        }
}
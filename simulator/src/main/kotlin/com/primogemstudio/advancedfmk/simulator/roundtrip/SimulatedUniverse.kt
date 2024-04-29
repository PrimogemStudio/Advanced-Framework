package com.primogemstudio.advancedfmk.simulator.roundtrip

import com.primogemstudio.advancedfmk.simulator.ContextWrapper
import com.primogemstudio.advancedfmk.simulator.ResultWrapper
import com.primogemstudio.advancedfmk.simulator.Simulator
import java.util.*

class SimulatedUniverse(
    val characters: MutableList<CharacterBase> = mutableListOf(),
    val enemies: MutableList<CharacterBase> = mutableListOf(),
    private val operateQueue: Deque<MutableList<CharacterBase>> = LinkedList()
): Simulator({
    enemies.flatMap { if (!it.alive()) listOf(it) else listOf() }
        .forEach { enemies.remove(it) }
    characters.flatMap { if (!it.alive()) listOf(it) else listOf() }
        .forEach { chr -> operateQueue.forEach { it.remove(chr) } }

    val tempData = mutableListOf<MutableList<CharacterBase>>()
    operateQueue.forEach { if (it.isEmpty()) tempData.add(it) }
    tempData.forEach { operateQueue.remove(it) }

    ResultWrapper(
        characters.map { it.calcHealth() }.sum() == 0f ||
                enemies.map { it.calcHealth() }.sum() == 0f
    )
}) {
    val operationStack: Stack<OperationDataWrapper> = Stack()
    fun buildQueue() {
        if (operateQueue.isNotEmpty()) return
        characters.filter { it.alive() }.forEach { operateQueue.offer(mutableListOf(it)) }
        enemies.forEach { operateQueue.offer(mutableListOf(it)) }
    }
    init { buildQueue() }
    fun getCurrentChar(): CharacterBase {
        buildQueue()
        return operateQueue.peek()[0]
    }
    fun getTargets(): MutableList<CharacterBase> {
        return when (getCurrentChar().type()) {
            CharacterBase.Type.Controllable -> enemies
            CharacterBase.Type.UnControllable -> characters
            CharacterBase.Type.UnControllableUnrequired -> characters
        }
    }
    override fun simulateStep(context: ContextWrapper) {
        buildQueue()

        val cu = operateQueue.peek()
        val current = cu[0]
        run {
            when (current.type()) {
                CharacterBase.Type.Controllable -> enemies
                CharacterBase.Type.UnControllable -> characters
                CharacterBase.Type.UnControllableUnrequired -> characters
            }.apply {
                operationStack.push(current.simulateStep(
                    TargetRequestContextWrapper(
                        this@SimulatedUniverse, current, this
                    )
                ))
            }
        }
        cu.removeAt(0)
        if (cu.size <= 0) operateQueue.poll()
    }

    public override fun clone(): SimulatedUniverse =  SimulatedUniverse(
            characters.map { it.clone() }.toMutableList(),
            enemies.map { it.clone() }.toMutableList(),
            operateQueue
        ).apply {
            this@apply.operationStack.addAll(this@SimulatedUniverse.operationStack)
            this@apply.characters.map { it.simulator = this@apply }
            this@apply.enemies.map { it.simulator = this@apply }
        }
}
package com.primogemstudio.advancedfmk.kui.animation

import com.primogemstudio.advancedfmk.kui.elements.RealElement
import kotlinx.coroutines.runBlocking

class ElemLockEvent(elem: RealElement): CustomAnimationEvent<Float>({ runBlocking { elem.renderLock.lock() } })
class ElemUnlockEvent(elem: RealElement): CustomAnimationEvent<Float>({ elem.renderLock.unlock() })
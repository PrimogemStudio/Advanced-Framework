package com.primogemstudio.advancedfmk.kui.animation

import com.primogemstudio.advancedfmk.kui.elements.UIElement

data class AnimatedObject(
    val obj: UIElement,
    val animationEvents: List<AnimationEvent<*>>
)
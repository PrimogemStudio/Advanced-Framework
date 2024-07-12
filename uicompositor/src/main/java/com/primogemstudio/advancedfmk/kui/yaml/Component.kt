package com.primogemstudio.advancedfmk.kui.yaml

enum class ComponentType {
    GROUP,
    TEXT,
    RECTANGLE
}

data class UIRoot(
    val className: String,
    val rootName: String,
    val component: Component?
)

abstract class Component(
    open var type: ComponentType? = null
)

class GroupComponent(
    var components: Map<String, Component?>? = null
) : Component(ComponentType.GROUP)

class RectangleComponent(
    var pos: List<Float>? = null,
    var size: List<Float>? = null,
    var color: List<Float>? = null,
    var radius: Float? = null,
    var thickness: Float? = null,
    var smoothedge: Float? = null,
    var texture: String? = null,
    var filter: Map<String, String>? = null
) : Component(ComponentType.RECTANGLE)

class TextComponent(
    var pos: List<Float>? = null,
    var text: String? = null,
    var color: List<Float>? = null,
    var textsize: Int? = null
) : Component(ComponentType.TEXT)
package com.primogemstudio.advancedfmk.kui.yaml

import org.apache.logging.log4j.core.util.Integers
import org.joml.Vector4f

enum class ComponentType(val clz: String) {
    GROUP("com/primogemstudio/advancedfmk/kui/elements/GroupElement"),
    TEXT("com/primogemstudio/advancedfmk/kui/elements/TextElement"),
    RECTANGLE("com/primogemstudio/advancedfmk/kui/elements/RectangleElement"),
    GEOMETRY_LINE("com/primogemstudio/advancedfmk/kui/elements/GeometryLineElement");
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
    var textureUV: Vector4f? = null,
    var filter: Map<String, String>? = null
) : Component(ComponentType.RECTANGLE)

class TextComponent(
    var pos: List<Float>? = null,
    var text: String? = null,
    var color: List<Float>? = null,
    var textsize: Int? = null,
    var vanilla: Boolean? = false
) : Component(ComponentType.TEXT)

class GeometryLineComponent(
    var width: Float? = null,
    var color: List<Float>? = null,
    var points: List<List<Int>>? = null,
    var filter: Map<String, String>? = null,
) : Component(ComponentType.GEOMETRY_LINE)
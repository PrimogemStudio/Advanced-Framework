package com.primogemstudio.advancedfmk.kui.yaml

import org.joml.Vector4f

enum class ComponentType {
    GROUP,
    TEXT,
    RECTANGLE,
    GEOMETRY_LINE,
    LIVE2D;
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
    var name: String? = null,
    var components: Map<String, Component?>? = null
) : Component(ComponentType.GROUP)

class RectangleComponent(
    var name: String? = null,
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

class Live2DComponent(
    var name: String? = null,
    var pos: List<Float>? = null,
    var size: List<Float>? = null,
    var color: List<Float>? = null,
    var radius: Float? = null,
    var thickness: Float? = null,
    var smoothedge: Float? = null,
    var modelName: String? = null,
    var modelPath: String? = null,
    var textureUV: Vector4f? = null,
    var filter: Map<String, String>? = null
) : Component(ComponentType.LIVE2D)

class TextComponent(
    var name: String? = null,
    var pos: List<Float>? = null,
    var text: String? = null,
    var color: List<Float>? = null,
    var textsize: Int? = null,
    var vanilla: Boolean? = false
) : Component(ComponentType.TEXT)

class GeometryLineComponent(
    var name: String? = null,
    var width: Float? = null,
    var color: List<Float>? = null,
    var points: List<List<Int>>? = null,
    var filter: Map<String, String>? = null,
) : Component(ComponentType.GEOMETRY_LINE)
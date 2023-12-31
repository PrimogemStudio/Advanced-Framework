package com.primogemstudio.advancedui.mmd.io

import org.joml.Vector3f

class PMXRigidBody {
    var m_name = ""
    var m_englishName = ""
    var m_boneIndex = 0
    var m_group = 0.toByte()
    var m_collisionGroup = 0.toShort()
    var m_shape = Shape.Sphere
    var m_shapeSize = Vector3f()
    var m_translate = Vector3f()
    var m_rotate = Vector3f()
    var m_mass = 0.toFloat()
    var m_translateDimmer = 0.toFloat()
    var m_rotateDimmer = 0.toFloat()
    var m_repulsion = 0.toFloat()
    var m_friction = 0.toFloat()
    var m_op = Operation.Static
}

enum class Operation {
    Static,
    Dynamic,
    DynamicAndBoneMerge
}

enum class Shape {
    Sphere,
    Box,
    Capsule,
}
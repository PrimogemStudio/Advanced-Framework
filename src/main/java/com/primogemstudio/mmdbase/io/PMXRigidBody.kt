package com.primogemstudio.mmdbase.io

import org.joml.Vector3f

class PMXRigidBody {
    var m_name = ""
    var m_englishName = ""
    var m_boneIndex = 0
    var m_group: Byte = 0
    var m_collisionGroup: Short = 0
    var m_shape = Shape.Sphere
    var m_shapeSize = Vector3f()
    var m_translate = Vector3f()
    var m_rotate = Vector3f()
    var m_mass = 0f
    var m_translateDimmer = 0f
    var m_rotateDimmer = 0f
    var m_repulsion = 0f
    var m_friction = 0f
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
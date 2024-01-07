package com.primogemstudio.mmdbase.io

import org.joml.Vector3f

class PMXJoint {
    var m_name = ""
    var m_englishName = ""
    var m_type = JointType.SpringDOF6
    var m_rigidbodyAIndex = 0
    var m_rigidbodyBIndex = 0
    var m_translate = Vector3f()
    var m_rotate = Vector3f()
    var m_translateLowerLimit = Vector3f()
    var m_translateUpperLimit = Vector3f()
    var m_rotateLowerLimit = Vector3f()
    var m_rotateUpperLimit = Vector3f()
    var m_springTranslateFactor = Vector3f()
    var m_springRotateFactor = Vector3f()
}

enum class JointType {
    SpringDOF6,
    DOF6,
    P2P,
    ConeTwist,
    Slider,
    Hinge,
}
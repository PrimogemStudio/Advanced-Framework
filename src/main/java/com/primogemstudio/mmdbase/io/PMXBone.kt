package com.primogemstudio.mmdbase.io

import org.joml.Vector3f

class PMXBone {
    var m_name = ""
    var m_englishName = ""
    var m_position = Vector3f()
    var m_parentBoneIndex = 0
    var m_deformDepth = 0
    var m_boneFlag = 0
    var m_positionOffset = Vector3f()
    var m_linkBoneIndex = 0
    var m_appendBoneIndex = 0
    var m_appendWeight = 0f
    var m_fixedAxis = Vector3f()
    var m_localXAxis = Vector3f()
    var m_localZAxis = Vector3f()
    var m_keyValue = 0
    var m_ikTargetBoneIndex = 0
    var m_ikIterationCount = 0
    var m_ikLimit = 0f
    var m_ikLinks = emptyArray<PMXIKLink>()
}

enum class PMXBoneFlags(val flag: Int) {
    TargetShowMode(0x0001),
    AllowRotate(0x0002),
    AllowTranslate(0x0004),
    Visible(0x0008),
    AllowControl(0x0010),
    IK(0x0020),
    AppendLocal(0x0080),
    AppendRotate(0x0100),
    AppendTranslate(0x0200),
    FixedAxis(0x0400),
    LocalAxis(0x800),
    DeformAfterPhysics(0x1000),
    DeformOuterParent(0x2000);
}

class PMXIKLink {
    var m_ikBoneIndex = 0
    var m_enableLimit: Byte = 0

    var m_limitMin = Vector3f()
    var m_limitMax = Vector3f()
}
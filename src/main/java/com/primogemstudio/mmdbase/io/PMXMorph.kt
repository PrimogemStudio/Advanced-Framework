package com.primogemstudio.mmdbase.io

import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f

class PMXMorph {
    var m_name = ""
    var m_englishName = ""
    var m_controlPanel: Byte = 0
    var m_morphType = PMXMorphType.Group
    var m_positionMorph = emptyArray<PositionMorph>()
    var m_uvMorph = emptyArray<UVMorph>()
    var m_boneMorph = emptyArray<BoneMorph>()
    var m_materialMorph = emptyArray<MaterialMorph>()
    var m_groupMorph = emptyArray<GroupMorph>()
    var m_flipMorph = emptyArray<FlipMorph>()
    var m_impulseMorph = emptyArray<ImpulseMorph>()
}

enum class PMXMorphType {
    Group,
    Position,
    Bone,
    UV,
    AddUV1,
    AddUV2,
    AddUV3,
    AddUV4,
    Material,
    Flip,
    Impluse
}

class PositionMorph {
    var m_vertexIndex = 0
    var m_position = Vector3f()
}

class UVMorph {
    var m_vertexIndex = 0
    var m_uv = Vector4f()
}

class BoneMorph {
    var m_boneIndex = 0
    var m_position = Vector3f()
    var m_quaternion = Quaternionf()
}

class MaterialMorph {
    enum class OpType {
        Mul,
        Add,
    }

    var m_materialIndex = 0
    var m_opType = OpType.Mul
    var m_diffuse = Vector4f()
    var m_specular = Vector3f()
    var m_specularPower = 0f
    var m_ambient = Vector3f()
    var m_edgeColor = Vector4f()
    var m_edgeSize = 0f
    var m_textureFactor = Vector4f()
    var m_sphereTextureFactor = Vector4f()
    var m_toonTextureFactor = Vector4f()
}

class GroupMorph {
    var	m_morphIndex = 0
    var m_weight = 0f
}

class FlipMorph {
    var	m_morphIndex = 0
    var m_weight = 0f
}

class ImpulseMorph {
    var m_rigidbodyIndex = 0
    var m_localFlag: Byte = 0	//0:OFF 1:ON
    var m_translateVelocity = Vector3f()
    var m_rotateTorque = Vector3f()
}
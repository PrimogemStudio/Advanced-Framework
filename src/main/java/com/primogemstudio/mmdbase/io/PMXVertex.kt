package com.primogemstudio.mmdbase.io

import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f

enum class PMXVertexWeight {
    BDEF1, BDEF2, BDEF4, SDEF, QDEF,
}

class PMXVertex {
    val m_position = Vector3f()
    val m_normal = Vector3f()
    val m_uv = Vector2f()
    val m_addUV = arrayOf(Vector4f(), Vector4f(), Vector4f(), Vector4f())
    var m_weightType = PMXVertexWeight.BDEF1
    val m_boneIndices = arrayOf(0, 0, 0, 0)
    val m_boneWeights = Vector4f()
    val m_sdefC = Vector3f()
    val m_sdefR0 = Vector3f()
    val m_sdefR1 = Vector3f()
    var m_edgeMag = 0f
}
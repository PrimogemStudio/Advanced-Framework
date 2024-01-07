package com.primogemstudio.mmdbase.io

import org.joml.Vector3f
import org.joml.Vector4f

class PMXMaterial {
    var m_name = ""
    var m_englishName = ""
    var m_diffuse = Vector4f()
    var m_specular = Vector3f()
    var m_specularPower = 0f
    var m_ambient = Vector3f()
    var m_drawMode = PMXDrawModeFlags.BothFace
    var m_edgeColor = Vector4f()
    var m_edgeSize = 0f
    var m_textureIndex = 0
    var m_sphereTextureIndex = 0
    var m_sphereMode = PMXSphereMode.None
    var m_toonMode = PMXToonMode.Common
    var m_toonTextureIndex = 0
    var m_memo = ""
    var m_numFaceVertices = 0
}

enum class PMXToonMode {
    Separate,
    Common,
}

enum class PMXSphereMode {
    None,
    Mul,
    Add,
    SubTexture,
}

enum class PMXDrawModeFlags {
    BothFace,
    GroundShadow,
    CastSelfShadow,
    RecieveSelfShadow,
    DrawEdge,
    VertexColor,
    DrawPoint,
    DrawLine;
    companion object {
        @JvmStatic
        fun findMode(a: Byte): PMXDrawModeFlags {
            return when (a.toInt()) {
                0x01 -> BothFace
                0x02 -> GroundShadow
                0x04 -> CastSelfShadow
                0x08 -> RecieveSelfShadow
                0x10 -> DrawEdge
                0x20 -> VertexColor
                0x40 -> DrawPoint
                0x80 -> DrawLine
                else -> BothFace
            }
        }
    }
}
package com.primogemstudio.mmdbase.io

class PMXSoftBody {
    var m_name = ""
    var m_englishName = ""
    var m_type = SoftbodyType.TriMesh
    var m_materialIndex = 0
    var m_group: Byte = 0
    var m_collisionGroup: Short = 0
    var m_flag = SoftbodyMask.BLink
    var m_BLinkLength = 0
    var m_numClusters = 0
    var m_totalMass = 0f
    var m_collisionMargin = 0F
    var m_aeroModel = AeroModel.kAeroModelF_OneSided
    var m_VCF = 0f
    var m_DP = 0f
    var m_DG = 0f
    var m_LF = 0f
    var m_PR = 0f
    var m_VC = 0f
    var m_DF = 0f
    var m_MT = 0f
    var m_CHR = 0f
    var m_KHR = 0f
    var m_SHR = 0f
    var m_AHR = 0f

    var m_SRHR_CL = 0f
    var m_SKHR_CL = 0f
    var m_SSHR_CL = 0f
    var m_SR_SPLT_CL = 0f
    var m_SK_SPLT_CL = 0f
    var m_SS_SPLT_CL = 0f

    var m_V_IT = 0
    var m_P_IT = 0
    var m_D_IT = 0
    var m_C_IT = 0

    var m_LST = 0f
    var m_AST = 0f
    var m_VST = 0f
    var m_anchorRigidBodies = emptyArray<AnchorRigidbody>()
    var m_pinVertexIndices = emptyArray<Int>()
}

enum class SoftbodyType {
    TriMesh,
    Rope,
}

enum class SoftbodyMask {
    BLink,
    Cluster,
    HybridLink;
    companion object {
        @JvmStatic
        fun getMask(b: Byte): SoftbodyMask {
            return when (b.toInt()) {
                0x01 -> BLink
                0x02 -> Cluster
                0x04 -> HybridLink
                else -> BLink
            }
        }
    }
}

enum class AeroModel {
    kAeroModelV_TwoSided,
    kAeroModelV_OneSided,
    kAeroModelF_TwoSided,
    kAeroModelF_OneSided,
}

class AnchorRigidbody {
    var m_rigidBodyIndex = 0
    var m_vertexIndex = 0
    var	m_nearMode: Byte = 0 // 0:OFF 1:ON
}
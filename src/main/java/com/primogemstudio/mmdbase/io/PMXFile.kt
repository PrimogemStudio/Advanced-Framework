package com.primogemstudio.mmdbase.io

import com.primogemstudio.mmdbase.abstraction.ITextureManager

class PMXHeader {
    var m_magic = ""
    var m_version = 0f
    var m_dataSize: Byte = 0
    var m_encode: Byte = 0
    var m_addUVNum: Byte = 0
    var m_vertexIndexSize: Byte = 0
    var m_textureIndexSize: Byte = 0
    var m_materialIndexSize: Byte = 0
    var m_boneIndexSize: Byte = 0
    var m_morphIndexSize: Byte = 0
    var m_rigidbodyIndexSize: Byte = 0
}

class PMXInfo {
    var m_modelName = ""
    var m_englishModelName = ""
    var m_comment = ""
    var m_englishComment = ""
}

class PMXFile {
    var m_header = PMXHeader()
    var m_info = PMXInfo()
    var m_vertices = emptyArray<PMXVertex>()
    var m_faces = emptyArray<PMXFace>()
    var m_textures = emptyArray<String>()
    var m_materials = emptyArray<PMXMaterial>()
    var m_bones = emptyArray<PMXBone>()
    var m_morphs = emptyArray<PMXMorph>()
    var m_displayFrames = emptyArray<PMXDisplayFrame>()
    var m_rigidbodies = emptyArray<PMXRigidBody>()
    var m_joints = emptyArray<PMXJoint>()
    var m_softbodies = emptyArray<PMXSoftBody>()
    var textureManager: ITextureManager? = null
}
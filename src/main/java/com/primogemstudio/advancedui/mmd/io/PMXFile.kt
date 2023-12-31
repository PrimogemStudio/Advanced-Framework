package com.primogemstudio.advancedui.mmd.io

class PMXHeader {
    var m_magic = ""
    var m_version = 0f
    var m_dataSize = 0.toByte()
    var m_encode = 0.toByte()
    var m_addUVNum = 0.toByte()
    var m_vertexIndexSize = 0.toByte()
    var m_textureIndexSize = 0.toByte()
    var m_materialIndexSize = 0.toByte()
    var m_boneIndexSize = 0.toByte()
    var m_morphIndexSize = 0.toByte()
    var m_rigidbodyIndexSize = 0.toByte()
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
}
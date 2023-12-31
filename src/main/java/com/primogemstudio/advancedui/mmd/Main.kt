package com.primogemstudio.advancedui.mmd

import com.primogemstudio.advancedui.mmd.io.ModelDataInputStream
import java.nio.file.Files
import java.nio.file.Path

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val pth = "D:\\Windows 文件夹\\下载\\lumine module\\lumine.pmx"
            val model = ModelDataInputStream(Files.newInputStream(Path.of(pth)))
            val pmx = model.readPMXFile()
            with(pmx.m_header) {
                println("m_magic: $m_magic")
                println("m_version: $m_version")
                println("m_dataSize: $m_dataSize")
                println("m_encode (0 -> UTF16, 1 -> UTF8): $m_encode")
                println("m_addUVNum: $m_addUVNum")
                println("m_vertexIndexSize: $m_vertexIndexSize")
                println("m_textureIndexSize: $m_textureIndexSize")
                println("m_materialIndexSize: $m_materialIndexSize")
                println("m_boneIndexSize: $m_boneIndexSize")
                println("m_morphIndexSize: $m_morphIndexSize")
                println("m_rigidbodyIndexSize: $m_rigidbodyIndexSize")
            }
            with(pmx.m_info) {
                println("m_modelName: $m_modelName")
                println("m_englishModelName: $m_englishModelName")
                println("m_comment: $m_comment")
                println("m_englishComment: $m_englishComment")
            }
            println("mdl_vertex_count: ${pmx.m_vertices.size}")
            println("mdl_face_count: ${pmx.m_faces.size}")
            println("mdl_texture_count: ${pmx.m_textures.size}")
            println("mdl_material_count: ${pmx.m_materials.size}")
            println("mdl_bone_count: ${pmx.m_bones.size}")
            println("mdl_morph_count: ${pmx.m_morphs.size}")
            println("mdl_displayframe_count: ${pmx.m_displayFrames.size}")
            println("mdl_rigidbody_count: ${pmx.m_rigidbodies.size}")
            println("mdl_joint_count: ${pmx.m_joints.size}")
        }
    }
}
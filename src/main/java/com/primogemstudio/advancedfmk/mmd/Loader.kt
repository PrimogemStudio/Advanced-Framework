package com.primogemstudio.advancedfmk.mmd

import com.mojang.blaze3d.platform.NativeImage
import com.primogemstudio.advancedfmk.mmd.renderer.MMDTexture
import com.primogemstudio.advancedfmk.mmd.renderer.TextureManager
import com.primogemstudio.mmdbase.io.ModelDataInputStream
import com.primogemstudio.mmdbase.io.PMXFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

object Loader {
    @JvmStatic
    fun load(): Pair<ModelDataInputStream, PMXFile> {
        val root = "D:\\360极速浏览器X下载\\【女主角_荧】_by_原神_44aee89b335a6bcb7f0183dbfdeab3e5"
        val name = "lumine.pmx"
        val model = ModelDataInputStream(Files.newInputStream(Path.of(root, name)))
        val pmx = model.readPMXFile()
        var sum = 0
        val tex = MMDTexture(pmx.m_textures.map { NativeImage.read(File(root, it).inputStream()) })
        pmx.textureManager = TextureManager(tex)
        pmx.m_materials.forEach {
            val tmp = it.m_numFaceVertices / 3
            for (i in sum until sum + tmp) {
                pmx.m_faces[i].m_vertices.forEach { vi ->
                    tex.mapping(pmx.m_vertices[vi].m_uv, it.m_textureIndex)
                }
            }
            sum += tmp
        }
        pmx.textureManager!!.register("mmd_lumine")
        return Pair(model, pmx)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val pck = load()
        val model = pck.first
        val pmx = pck.second
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
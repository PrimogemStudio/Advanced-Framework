package com.primogemstudio.mmdbase.io

import com.primogemstudio.mmdbase.io.PMXVertexWeight.*
import org.joml.Quaternionf
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import java.io.DataInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.StandardCharsets

class ModelDataInputStream(flow: InputStream) : DataInputStream(flow) {
    private fun readLEInt(): Int {
        return readNLEInts(1)[0]
    }

    private fun readLEFloat(): Float {
        return readNLEFloats(1)[0]
    }

    private fun readLEShort(): Short {
        return readNLEShorts(1)[0]
    }

    private fun readText(isUtf16: Boolean): String {
        val length = readLEInt()
        return String(readNBytes(length), if (isUtf16) StandardCharsets.UTF_16LE else StandardCharsets.UTF_8)
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun readNLEFloats(n: Int): FloatArray {
        val buf = ByteBuffer.wrap(readNBytes(n * 4)).order(ByteOrder.LITTLE_ENDIAN)
        val arr = FloatArray(n)
        for (i in 0 until n) arr[i] = buf.getFloat(i * 4)
        return arr
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun readNLEShorts(n: Int): ShortArray {
        val buf = ByteBuffer.wrap(readNBytes(n * 2)).order(ByteOrder.LITTLE_ENDIAN)
        val arr = ShortArray(n)
        for (i in 0 until n) arr[i] = buf.getShort(i * 2)
        return arr
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun readNLEInts(n: Int): IntArray {
        val buf = ByteBuffer.wrap(readNBytes(n * 4)).order(ByteOrder.LITTLE_ENDIAN)
        val arr = IntArray(n)
        for (i in 0 until n) arr[i] = buf.getInt(i * 4)
        return arr
    }

    private fun readVec2(input: Vector2f) {
        input.set(readNLEFloats(2))
    }

    private fun readVec3(input: Vector3f) {
        input.set(readNLEFloats(3))
    }

    private fun readVec4(input: Vector4f) {
        input.set(readNLEFloats(4))
    }

    private fun readQuaternion(input: Quaternionf) {
        val buf = readNLEFloats(4)
        input.set(buf[0], buf[1], buf[2], buf[3])
    }

    private fun readHeader(header: PMXHeader) {
        header.m_magic = String(readNBytes(4))
        header.m_version = readLEFloat()
        header.m_dataSize = readByte()
        header.m_encode = readByte()
        header.m_addUVNum = readByte()
        header.m_vertexIndexSize = readByte()
        header.m_textureIndexSize = readByte()
        header.m_materialIndexSize = readByte()
        header.m_boneIndexSize = readByte()
        header.m_morphIndexSize = readByte()
        header.m_rigidbodyIndexSize = readByte()
    }

    private fun readInfo(info: PMXInfo, isU16: Boolean) {
        info.m_modelName = readText(isU16)
        info.m_englishModelName = readText(isU16)
        info.m_comment = readText(isU16)
        info.m_englishComment = readText(isU16)
    }

    private fun readIndex(size: Int): Int {
        val arr = Array(1) { 0 }
        readIndex(arr, 0, size)
        return arr[0]
    }

    private fun readIndex(index: Array<Int>, offset: Int, size: Int) {
        when (size) {
            1 -> {
                val idx = readByte()
                if (idx != 0xFF.toByte()) {
                    index[offset] = idx.toInt()
                } else {
                    index[offset] = -1
                }
            }

            2 -> {
                val idx = readLEShort()
                if (idx != 0xFFFF.toShort()) {
                    index[offset] = idx.toInt()
                } else {
                    index[offset] = -1
                }
            }

            4 -> index[offset] = readLEInt()
        }
    }

    private fun readVertices(vertices: Array<PMXVertex>, header: PMXHeader) {
        for (vertex in vertices) {
            readVec3(vertex.m_position)
            readVec3(vertex.m_normal)
            readVec2(vertex.m_uv)
            for (i in 0 until header.m_addUVNum) {
                readVec4(vertex.m_addUV[i])
            }
            vertex.m_weightType = PMXVertexWeight.entries[readByte().toInt()]
            when (vertex.m_weightType) {
                BDEF1 -> readIndex(vertex.m_boneIndices, 0, header.m_boneIndexSize.toInt())
                BDEF2 -> {
                    readIndex(vertex.m_boneIndices, 0, header.m_boneIndexSize.toInt())
                    readIndex(vertex.m_boneIndices, 1, header.m_boneIndexSize.toInt())
                    vertex.m_boneWeights.x = readLEFloat()
                }

                BDEF4, QDEF -> {
                    readIndex(vertex.m_boneIndices, 0, header.m_boneIndexSize.toInt())
                    readIndex(vertex.m_boneIndices, 1, header.m_boneIndexSize.toInt())
                    readIndex(vertex.m_boneIndices, 2, header.m_boneIndexSize.toInt())
                    readIndex(vertex.m_boneIndices, 3, header.m_boneIndexSize.toInt())
                    readVec4(vertex.m_boneWeights)
                }

                SDEF -> {
                    readIndex(vertex.m_boneIndices, 0, header.m_boneIndexSize.toInt())
                    readIndex(vertex.m_boneIndices, 1, header.m_boneIndexSize.toInt())
                    vertex.m_boneWeights.x = readLEFloat()
                    readVec3(vertex.m_sdefC)
                    readVec3(vertex.m_sdefR0)
                    readVec3(vertex.m_sdefR1)
                }
            }
            vertex.m_edgeMag = readLEFloat()
        }
    }

    private fun readFaces(face: Array<PMXFace>, header: PMXHeader) {
        val arr = when (header.m_vertexIndexSize) {
            1.toByte() -> readNBytes(face.size * 3)
            2.toByte() -> readNLEShorts(face.size * 3)
            4.toByte() -> readNLEInts(face.size * 3)
            else -> TODO("Unknown data size: ${header.m_vertexIndexSize}")
        }
        for (faceIdx in face.indices) {
            for (i in 0 until 3) face[faceIdx].m_vertices[i] = arr.fetchInt(faceIdx * 3 + i)
        }
    }

    private fun readTextures(textures: Array<String>, header: PMXHeader) {
        for (i in textures.indices) textures[i] = readText(header.m_encode == 0.toByte())
    }

    private fun readMaterials(materials: Array<PMXMaterial>, header: PMXHeader) {
        for (i in materials.indices) {
            materials[i].m_name = readText(header.m_encode == 0.toByte())
            materials[i].m_englishName = readText(header.m_encode == 0.toByte())
            readVec4(materials[i].m_diffuse)
            readVec3(materials[i].m_specular)
            materials[i].m_specularPower = readLEFloat()
            readVec3(materials[i].m_ambient)
            materials[i].m_drawMode = PMXDrawModeFlags.findMode(readByte())
            readVec4(materials[i].m_edgeColor)
            materials[i].m_edgeSize = readLEFloat()
            materials[i].m_textureIndex = readIndex(header.m_textureIndexSize.toInt())
            materials[i].m_sphereTextureIndex = readIndex(header.m_textureIndexSize.toInt())
            materials[i].m_sphereMode = PMXSphereMode.entries[readByte().toInt()]
            materials[i].m_toonMode = PMXToonMode.entries[readByte().toInt()]
            materials[i].m_toonTextureIndex = when (materials[i].m_toonMode) {
                PMXToonMode.Separate -> readIndex(header.m_textureIndexSize.toInt())
                PMXToonMode.Common -> readByte().toInt()
            }

            materials[i].m_memo = readText(header.m_encode == 0.toByte())
            materials[i].m_numFaceVertices = readLEInt()
        }
    }

    private fun readBones(bones: Array<PMXBone>, header: PMXHeader) {
        for (i in bones.indices) {
            bones[i].m_name = readText(header.m_encode == 0.toByte())
            bones[i].m_englishName = readText(header.m_encode == 0.toByte())
            readVec3(bones[i].m_position)
            bones[i].m_parentBoneIndex = readIndex(header.m_boneIndexSize.toInt())
            bones[i].m_deformDepth = readLEInt()
            bones[i].m_boneFlag = readLEShort().toInt()

            val flag = bones[i].m_boneFlag

            if (flag and PMXBoneFlags.TargetShowMode.flag == 0) readVec3(bones[i].m_positionOffset)
            else bones[i].m_linkBoneIndex = readIndex(header.m_boneIndexSize.toInt())

            if ((flag and PMXBoneFlags.AppendRotate.flag) != 0 || (flag and PMXBoneFlags.AppendTranslate.flag) != 0) {
                bones[i].m_appendBoneIndex = readIndex(header.m_boneIndexSize.toInt())
                bones[i].m_appendWeight = readLEFloat()
            }

            if ((flag and PMXBoneFlags.FixedAxis.flag) != 0) readVec3(bones[i].m_fixedAxis)
            if ((flag and PMXBoneFlags.LocalAxis.flag) != 0) {
                readVec3(bones[i].m_localXAxis)
                readVec3(bones[i].m_localZAxis)
            }
            if ((flag and PMXBoneFlags.DeformOuterParent.flag) != 0) bones[i].m_keyValue = readLEInt()
            if ((flag and PMXBoneFlags.IK.flag) != 0) {
                bones[i].m_ikTargetBoneIndex = readIndex(header.m_boneIndexSize.toInt())
                bones[i].m_ikIterationCount = readLEInt()
                bones[i].m_ikLimit = readLEFloat()

                val lnk_siz = readLEInt()
                bones[i].m_ikLinks = Array(lnk_siz) { PMXIKLink() }
                for (j in bones[i].m_ikLinks.indices) {
                    bones[i].m_ikLinks[j].m_ikBoneIndex = readIndex(header.m_boneIndexSize.toInt())
                    bones[i].m_ikLinks[j].m_enableLimit = readByte()
                    if (bones[i].m_ikLinks[j].m_enableLimit.toUByte() != 0.toUByte()) {
                        readVec3(bones[i].m_ikLinks[j].m_limitMin)
                        readVec3(bones[i].m_ikLinks[j].m_limitMax)
                    }
                }
            }
        }
    }

    private fun readMorphs(morphs: Array<PMXMorph>, header: PMXHeader) {
        for (i in morphs.indices) {
            morphs[i].m_name = readText(header.m_encode == 0.toByte())
            morphs[i].m_englishName = readText(header.m_encode == 0.toByte())
            morphs[i].m_controlPanel = readByte()
            morphs[i].m_morphType = PMXMorphType.entries[readByte().toInt()]
            val data_cnt = readLEInt()
            val type = morphs[i].m_morphType
            when {
                type == PMXMorphType.Position -> {
                    morphs[i].m_positionMorph = Array(data_cnt) { PositionMorph() }
                    for (j in morphs[i].m_positionMorph.indices) {
                        morphs[i].m_positionMorph[j].m_vertexIndex = readIndex(header.m_vertexIndexSize.toInt())
                        readVec3(morphs[i].m_positionMorph[j].m_position)
                    }
                }

                setOf(
                    PMXMorphType.UV, PMXMorphType.AddUV1, PMXMorphType.AddUV2, PMXMorphType.AddUV3, PMXMorphType.AddUV4
                ).contains(type) -> {
                    morphs[i].m_uvMorph = Array(data_cnt) { UVMorph() }
                    for (j in morphs[i].m_uvMorph.indices) {
                        morphs[i].m_uvMorph[j].m_vertexIndex = readIndex(header.m_vertexIndexSize.toInt())
                        readVec4(morphs[i].m_uvMorph[j].m_uv)
                    }
                }

                type == PMXMorphType.Bone -> {
                    morphs[i].m_boneMorph = Array(data_cnt) { BoneMorph() }
                    for (j in morphs[i].m_boneMorph.indices) {
                        morphs[i].m_boneMorph[j].m_boneIndex = readIndex(header.m_boneIndexSize.toInt())
                        readVec3(morphs[i].m_boneMorph[j].m_position)
                        readQuaternion(morphs[i].m_boneMorph[j].m_quaternion)
                    }
                }

                type == PMXMorphType.Material -> {
                    morphs[i].m_materialMorph = Array(data_cnt) { MaterialMorph() }
                    for (j in morphs[i].m_materialMorph.indices) {
                        morphs[i].m_materialMorph[j].m_materialIndex = readIndex(header.m_materialIndexSize.toInt())
                        morphs[i].m_materialMorph[j].m_opType = MaterialMorph.OpType.entries[readByte().toInt()]
                        readVec4(morphs[i].m_materialMorph[j].m_diffuse)
                        readVec3(morphs[i].m_materialMorph[j].m_specular)
                        morphs[i].m_materialMorph[j].m_specularPower = readLEFloat()
                        readVec3(morphs[i].m_materialMorph[j].m_ambient)
                        readVec4(morphs[i].m_materialMorph[j].m_edgeColor)
                        morphs[i].m_materialMorph[j].m_edgeSize = readLEFloat()
                        readVec4(morphs[i].m_materialMorph[j].m_textureFactor)
                        readVec4(morphs[i].m_materialMorph[j].m_sphereTextureFactor)
                        readVec4(morphs[i].m_materialMorph[j].m_toonTextureFactor)
                    }
                }

                type == PMXMorphType.Group -> {
                    morphs[i].m_groupMorph = Array(data_cnt) { GroupMorph() }
                    for (j in morphs[i].m_groupMorph.indices) {
                        morphs[i].m_groupMorph[j].m_morphIndex = readIndex(header.m_morphIndexSize.toInt())
                        morphs[i].m_groupMorph[j].m_weight = readLEFloat()
                    }
                }

                type == PMXMorphType.Flip -> {
                    morphs[i].m_flipMorph = Array(data_cnt) { FlipMorph() }
                    for (j in morphs[i].m_flipMorph.indices) {
                        morphs[i].m_flipMorph[j].m_morphIndex = readIndex(header.m_morphIndexSize.toInt())
                        morphs[i].m_flipMorph[j].m_weight = readLEFloat()
                    }
                }

                type == PMXMorphType.Impluse -> {
                    morphs[i].m_impulseMorph = Array(data_cnt) { ImpulseMorph() }
                    for (j in morphs[i].m_impulseMorph.indices) {
                        morphs[i].m_impulseMorph[j].m_rigidbodyIndex = readIndex(header.m_rigidbodyIndexSize.toInt())
                        morphs[i].m_impulseMorph[j].m_localFlag = readByte()
                        readVec3(morphs[i].m_impulseMorph[j].m_translateVelocity)
                        readVec3(morphs[i].m_impulseMorph[j].m_rotateTorque)
                    }
                }

                else -> TODO("Unknown morph type")
            }
        }
    }

    private fun readDisplayFrames(frames: Array<PMXDisplayFrame>, header: PMXHeader) {
        for (i in frames.indices) {
            frames[i].m_name = readText(header.m_encode == 0.toByte())
            frames[i].m_englishName = readText(header.m_encode == 0.toByte())
            frames[i].m_flag = FrameType.entries[readByte().toInt()]
            val siz = readLEInt()
            frames[i].m_targets = Array(siz) { Target() }
            for (j in frames[i].m_targets.indices) {
                frames[i].m_targets[j].m_type = TargetType.entries[readByte().toInt()]
                frames[i].m_targets[j].m_index = readIndex(
                    when (frames[i].m_targets[j].m_type) {
                        TargetType.BoneIndex -> header.m_boneIndexSize
                        TargetType.MorphIndex -> header.m_morphIndexSize
                    }.toInt()
                )
            }
        }
    }

    private fun readRigidBodies(rigidbodies: Array<PMXRigidBody>, header: PMXHeader) {
        for (i in rigidbodies.indices) {
            rigidbodies[i].m_name = readText(header.m_encode == 0.toByte())
            rigidbodies[i].m_englishName = readText(header.m_encode == 0.toByte())
            rigidbodies[i].m_boneIndex = readIndex(header.m_boneIndexSize.toInt())
            rigidbodies[i].m_group = readByte()
            rigidbodies[i].m_collisionGroup = readLEShort()
            rigidbodies[i].m_shape = Shape.entries[readByte().toInt()]
            readVec3(rigidbodies[i].m_shapeSize)
            readVec3(rigidbodies[i].m_translate)
            readVec3(rigidbodies[i].m_rotate)

            rigidbodies[i].m_mass = readLEFloat()
            rigidbodies[i].m_translateDimmer = readLEFloat()
            rigidbodies[i].m_rotateDimmer = readLEFloat()
            rigidbodies[i].m_repulsion = readLEFloat()
            rigidbodies[i].m_friction = readLEFloat()
            rigidbodies[i].m_op = Operation.entries[readByte().toInt()]
        }
    }

    private fun readJoints(joints: Array<PMXJoint>, header: PMXHeader) {
        for (i in joints.indices) {
            joints[i].m_name = readText(header.m_encode == 0.toByte())
            joints[i].m_englishName = readText(header.m_encode == 0.toByte())
            joints[i].m_type = JointType.entries[readByte().toInt()]
            joints[i].m_rigidbodyAIndex = readIndex(header.m_rigidbodyIndexSize.toInt())
            joints[i].m_rigidbodyBIndex = readIndex(header.m_rigidbodyIndexSize.toInt())

            readVec3(joints[i].m_translate)
            readVec3(joints[i].m_rotate)
            readVec3(joints[i].m_translateLowerLimit)
            readVec3(joints[i].m_translateUpperLimit)
            readVec3(joints[i].m_rotateLowerLimit)
            readVec3(joints[i].m_rotateUpperLimit)
            readVec3(joints[i].m_springTranslateFactor)
            readVec3(joints[i].m_springRotateFactor)
        }
    }

    private fun readSoftBodies(bodies: Array<PMXSoftBody>, header: PMXHeader) {
        for (i in 0 until 1) {
            bodies[i].m_name = readText(header.m_encode == 0.toByte())
            bodies[i].m_englishName = readText(header.m_encode == 0.toByte())
            bodies[i].m_type = SoftbodyType.entries[readByte().toInt()]
            bodies[i].m_materialIndex = readIndex(header.m_materialIndexSize.toInt())
            bodies[i].m_group = readByte()
            bodies[i].m_collisionGroup = readLEShort()
            bodies[i].m_flag = SoftbodyMask.getMask(readByte())
            bodies[i].m_BLinkLength = readLEInt()
            bodies[i].m_numClusters = readLEInt()
            bodies[i].m_totalMass = readLEFloat()
            bodies[i].m_collisionMargin = readLEFloat()
            bodies[i].m_aeroModel = AeroModel.entries[readLEInt()]

            bodies[i].m_VCF = readLEFloat()
            bodies[i].m_DP = readLEFloat()
            bodies[i].m_DG = readLEFloat()
            bodies[i].m_LF = readLEFloat()
            bodies[i].m_PR = readLEFloat()
            bodies[i].m_VC = readLEFloat()
            bodies[i].m_DF = readLEFloat()
            bodies[i].m_MT = readLEFloat()
            bodies[i].m_CHR = readLEFloat()
            bodies[i].m_KHR = readLEFloat()
            bodies[i].m_SHR = readLEFloat()
            bodies[i].m_AHR = readLEFloat()

            bodies[i].m_SRHR_CL = readLEFloat()
            bodies[i].m_SKHR_CL = readLEFloat()
            bodies[i].m_SSHR_CL = readLEFloat()
            bodies[i].m_SR_SPLT_CL = readLEFloat()
            bodies[i].m_SK_SPLT_CL = readLEFloat()
            bodies[i].m_SS_SPLT_CL = readLEFloat()

            bodies[i].m_V_IT = readLEInt()
            bodies[i].m_P_IT = readLEInt()
            bodies[i].m_D_IT = readLEInt()
            bodies[i].m_C_IT = readLEInt()

            bodies[i].m_LST = readLEFloat()
            bodies[i].m_AST = readLEFloat()
            bodies[i].m_VST = readLEFloat()

            val ar_cnt = readLEInt()
            bodies[i].m_anchorRigidBodies = Array(ar_cnt) { AnchorRigidbody() }
            for (j in bodies[i].m_anchorRigidBodies.indices) {
                bodies[i].m_anchorRigidBodies[j].m_rigidBodyIndex = readIndex(header.m_rigidbodyIndexSize.toInt())
                bodies[i].m_anchorRigidBodies[j].m_vertexIndex = readIndex(header.m_vertexIndexSize.toInt())
                bodies[i].m_anchorRigidBodies[j].m_nearMode = readByte()
            }

            val pv_cnt = readLEInt()
            bodies[i].m_pinVertexIndices = Array(pv_cnt) { 0 }
            for (n in bodies[i].m_pinVertexIndices.indices) {
                bodies[i].m_pinVertexIndices[n] = readIndex(header.m_vertexIndexSize.toInt())
            }
        }
    }

    fun debugBytes(i: Int) {
        readNBytes(i).forEach { print(String.format("%02X ", it)) }
    }

    fun readPMXFile(): PMXFile {
        val file = PMXFile()
        readHeader(file.m_header)
        readInfo(file.m_info, file.m_header.m_encode == 0.toByte())
        file.m_vertices = Array(readLEInt()) { PMXVertex() }
        readVertices(file.m_vertices, file.m_header)
        file.m_faces = Array(readLEInt() / 3) { PMXFace() }
        readFaces(file.m_faces, file.m_header)
        file.m_textures = Array(readLEInt()) { "" }
        readTextures(file.m_textures, file.m_header)
        file.m_materials = Array(readLEInt()) { PMXMaterial() }
        readMaterials(file.m_materials, file.m_header)
        file.m_bones = Array(readLEInt()) { PMXBone() }
        readBones(file.m_bones, file.m_header)
        file.m_morphs = Array(readLEInt()) { PMXMorph() }
        readMorphs(file.m_morphs, file.m_header)
        file.m_displayFrames = Array(readLEInt()) { PMXDisplayFrame() }
        readDisplayFrames(file.m_displayFrames, file.m_header)
        file.m_rigidbodies = Array(readLEInt()) { PMXRigidBody() }
        readRigidBodies(file.m_rigidbodies, file.m_header)
        file.m_joints = Array(readLEInt()) { PMXJoint() }
        readJoints(file.m_joints, file.m_header)
        if (available() > 0) {
            file.m_softbodies = Array(readLEInt()) { PMXSoftBody() }
            readSoftBodies(file.m_softbodies, file.m_header)
        }

        return file
    }
}

private fun Any.fetchInt(i: Int): Int {
    if (this is ByteArray) return this[i].toInt()
    if (this is ShortArray) return this[i].toInt()
    if (this is IntArray) return this[i]
    if (this is LongArray) return this[i].toInt()
    if (this is FloatArray) return this[i].toInt()
    if (this is DoubleArray) return this[i].toInt()
    return 0
}

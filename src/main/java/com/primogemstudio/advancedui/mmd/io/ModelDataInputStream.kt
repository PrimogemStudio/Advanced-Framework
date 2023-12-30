package com.primogemstudio.advancedui.mmd.io

import com.primogemstudio.advancedui.mmd.io.PMXVertexWeight.*
import kool.toPtr
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

    private inline fun readNLEShorts(n: Int): ShortArray {
        val buf = ByteBuffer.wrap(readNBytes(n * 2)).order(ByteOrder.LITTLE_ENDIAN)
        val arr = ShortArray(n)
        for (i in 0 until n) arr[i] = buf.getShort(i * 2)
        return arr
    }

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

    fun readPMXFile(): PMXFile {
        val file = PMXFile()
        readHeader(file.m_header)
        readInfo(file.m_info, file.m_header.m_encode == 0.toByte())
        file.m_vertices = Array(readLEInt()) { PMXVertex() }
        readVertices(file.m_vertices, file.m_header)
        file.m_faces = Array(readLEInt() / 3) { PMXFace() }
        readFaces(file.m_faces, file.m_header)
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

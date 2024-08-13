package com.primogemstudio.advancedfmk.bin.moc3

import java.io.DataInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class MOC3InputStream(`in`: InputStream): DataInputStream(`in`) {
    val size = available()

    fun parseInt(be: Boolean): Int {
        if (be) return readInt()
        else {
            val buff = ByteBuffer.allocate(4)
            buff.putInt(readInt())
            buff.order(ByteOrder.LITTLE_ENDIAN)
            return buff.getInt(0)
        }
    }

    fun parseHeader(): MOC3Header {
        return MOC3Header(
            String(readNBytes(4)),
            MOC3Header.Version.get(readByte()),
            readByte() != 0.toByte()
        ).apply {
            if (magic != "MOC3") throw IllegalStateException("Magic wrong")
            if (version == null) throw IllegalStateException("Unrecognized version")

            this@MOC3InputStream.skipNBytes(58)
        }
    }

    fun parsePointerMap(header: MOC3Header): MOC3PointerMap {
        return MOC3PointerMap(
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            MOC3PartPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3DeformersPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3WarpDeformersPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3RotateDeformersPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3ArtMeshesPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3ParametersPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            parseInt(header.bigEndian),
            MOC3WarpDeformerKeyformsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3RotateDeformerKeyformsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3ArtMeshKeyformsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            MOC3KeyformsBindingsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3ParameterBindingsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            MOC3DrawOrderGroupsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3DrawOrderGroupObjectsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3GluePointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            MOC3GlueInfoPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ),
            parseInt(header.bigEndian),
            if (header.version?.flag!! >= MOC3Header.Version.V3_03_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) MOC3ParameterExtensionsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3ParameterExtensionsPointerMap(-1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) MOC3KeyformColorsMultiplyPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3KeyformColorsMultiplyPointerMap(-1, -1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) MOC3KeyformColorsScreenPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3KeyformColorsScreenPointerMap(-1, -1, -1)
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun test() {
        println("Current offset: 0x${(size - available()).toHexString()}")
        readNBytes(16).forEach {
            print("0x" + it.toHexString() + " ")
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun parse(): MOC3Model {
        return parseHeader().let {
            MOC3Model(
                it,
                parsePointerMap(it)
            )
        }.apply { println(parseInt(header.bigEndian).toHexString()); test() }
    }
}
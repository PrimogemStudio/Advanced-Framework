package com.primogemstudio.advancedfmk.bin.moc3

import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class MOC3InputStream(`in`: InputStream): DataInputStream(BufferedInputStream(`in`)) {
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
        mark(-1)
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

    @OptIn(ExperimentalStdlibApi::class)
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
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            )  else MOC3ParameterExtensionsPointerMap(-1, -1, -1),
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
            ) else MOC3KeyformColorsScreenPointerMap(-1, -1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) MOC3ParametersV42PointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3ParametersV42PointerMap(-1, -1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) MOC3BlendShapeParameterBindingsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3BlendShapeParameterBindingsPointerMap(-1, -1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) MOC3BlendShapeKeyformBindingsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3BlendShapeKeyformBindingsPointerMap(-1, -1, -1, -1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) MOC3BlendShapesWarpDeformersPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3BlendShapesWarpDeformersPointerMap(-1, -1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) MOC3BlendShapesArtMeshesPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3BlendShapesArtMeshesPointerMap(-1, -1, -1),
            parseInt(header.bigEndian),
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) MOC3BlendShapesConstraintsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3BlendShapesConstraintsPointerMap(-1, -1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) MOC3BlendShapesConstraintValuesPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3BlendShapesConstraintValuesPointerMap(-1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V5_00_00.flag) MOC3WarpDeformerKeyformsV50PointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3WarpDeformerKeyformsV50PointerMap(-1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V5_00_00.flag) MOC3RotateDeformerKeyformsV50PointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3RotateDeformerKeyformsV50PointerMap(-1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V5_00_00.flag) MOC3ArtMeshKeyformsV50PointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3ArtMeshKeyformsV50PointerMap(-1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V5_00_00.flag) MOC3BlendShapesPartsPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3BlendShapesPartsPointerMap(-1, -1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V5_00_00.flag) MOC3BlendShapesRotateDeformersPointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3BlendShapesRotateDeformersPointerMap(-1, -1, -1),
            if (header.version?.flag!! >= MOC3Header.Version.V5_00_00.flag) MOC3BlendShapesGluePointerMap(
                parseInt(header.bigEndian),
                parseInt(header.bigEndian),
                parseInt(header.bigEndian)
            ) else MOC3BlendShapesGluePointerMap(-1, -1, -1),
        )
    }

    fun parseCountInfoTableData(header: MOC3Header, pointers: MOC3PointerMap): MOC3CountInfoTableData {
        reset()
        mark(-1)
        skipNBytes(pointers.countInfoOffset.toLong())
        return MOC3CountInfoTableData(
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian)
        )
    }

    fun parseData(header: MOC3Header, pointers: MOC3PointerMap): MOC3Data = MOC3Data(
        parseCountInfoTableData(header, pointers)
    )

    fun parse(): MOC3Model {
        return parseHeader().let {
            val pointers = parsePointerMap(it)
            MOC3Model(
                it,
                pointers,
                parseData(it, pointers)
            )
        }
    }
}
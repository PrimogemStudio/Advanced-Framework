package com.primogemstudio.advancedfmk.bin.moc3

import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class MOC3InputStream(`in`: InputStream): DataInputStream(BufferedInputStream(`in`)) {
    init {
        mark(2147483647)
    }
    fun seekTo(pos: Int) {
        reset()
        mark(2147483647)
        skipNBytes(pos.toLong())
    }
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
        seekTo(pointers.countInfoOffset)
        return MOC3CountInfoTableData(
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
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V4_02_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V5_00_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V5_00_00.flag) parseInt(header.bigEndian) else -1,
            if (header.version?.flag!! >= MOC3Header.Version.V5_00_00.flag) parseInt(header.bigEndian) else -1
        )
    }

    fun parseCanvasInfo(header: MOC3Header, pointers: MOC3PointerMap): MOC3CanvasInfo {
        seekTo(pointers.canvasInfoOffset)
        return MOC3CanvasInfo(
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian),
            parseInt(header.bigEndian)
        )
    }

    fun parsePartsID(pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<String> {
        seekTo(pointers.partOffset.idOffset)

        return Array(counts.parts) { String(readNBytes(64)) }
    }

    fun parsePartKeyframeBindingSourceIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
       seekTo(pointers.partOffset.keyframeBindingSourceIndicesOffset)

        return Array(counts.parts) { parseInt(header.bigEndian) }
    }

    fun parsePartKeyframeSourcesBeginIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.partOffset.keyframeSourcesBeginIndicesOffset)

        return Array(counts.parts) { parseInt(header.bigEndian) }
    }

    fun parsePartKeyframeSourcesCount(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.partOffset.keyframeSourcesCountOffset)

        return Array(counts.parts) { parseInt(header.bigEndian) }
    }

    fun parsePartVisible(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Boolean> {
        seekTo(pointers.partOffset.visibleOffset)

        return Array(counts.parts) { parseInt(header.bigEndian) != 0 }
    }

    fun parsePartEnabled(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Boolean> {
        seekTo(pointers.partOffset.enabledOffset)

        return Array(counts.parts) { parseInt(header.bigEndian) != 0 }
    }

    fun parsePartParentPartIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.partOffset.parentPartIndicesOffset)

        return Array(counts.parts) { parseInt(header.bigEndian) }
    }

    fun parseParts(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): MOC3Parts {
        return MOC3Parts(
            parsePartsID(pointers, counts),
            parsePartKeyframeBindingSourceIndices(header, pointers, counts),
            parsePartKeyframeSourcesBeginIndices(header, pointers, counts),
            parsePartKeyframeSourcesCount(header, pointers, counts),
            parsePartVisible(header, pointers, counts),
            parsePartEnabled(header, pointers, counts),
            parsePartParentPartIndices(header, pointers, counts)
        )
    }

    fun parseDeformersID(pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<String> {
        seekTo(pointers.deformersOffset.idOffset)

        return Array(counts.deformers) { String(readNBytes(64)) }
    }

    fun parseDeformersKeyformBindingSourcesIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.deformersOffset.keyformBindingSourceIndicesOffset)

        return Array(counts.deformers) { parseInt(header.bigEndian) }
    }

    fun parseDeformersVisible(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Boolean> {
        seekTo(pointers.deformersOffset.visibleOffset)

        return Array(counts.deformers) { parseInt(header.bigEndian) != 0 }
    }

    fun parseDeformersEnabled(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Boolean> {
        seekTo(pointers.deformersOffset.enabledOffset)

        return Array(counts.deformers) { parseInt(header.bigEndian) != 0 }
    }

    fun parseDeformersParentPartIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.deformersOffset.parentPartIndicesOffset)

        return Array(counts.deformers) { parseInt(header.bigEndian) }
    }

    fun parseDeformersParentDeformerIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.deformersOffset.parentPartIndicesOffset)

        return Array(counts.deformers) { parseInt(header.bigEndian) }
    }

    fun parseDeformersType(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<MOC3DeformerType> {
        seekTo(pointers.deformersOffset.typesIndicesOffset)

        return Array(counts.deformers) { if (parseInt(header.bigEndian) == 1) MOC3DeformerType.ROTATE else MOC3DeformerType.WARP }
    }

    fun parseDeformersSpecificSourceIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.deformersOffset.specificSourcesIndicesOffset)

        return Array(counts.deformers) { parseInt(header.bigEndian) }
    }

    fun parseDeformers(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): MOC3Deformers {
        return MOC3Deformers(
            parseDeformersID(pointers, counts),
            parseDeformersKeyformBindingSourcesIndices(header, pointers, counts),
            parseDeformersVisible(header, pointers, counts),
            parseDeformersEnabled(header, pointers, counts),
            parseDeformersParentPartIndices(header, pointers, counts),
            parseDeformersParentDeformerIndices(header, pointers, counts),
            parseDeformersType(header, pointers, counts),
            parseDeformersSpecificSourceIndices(header, pointers, counts)
        )
    }

    fun parseData(header: MOC3Header, pointers: MOC3PointerMap): MOC3Data = parseCountInfoTableData(header, pointers).let {
        MOC3Data(
            it,
            parseCanvasInfo(header, pointers),
            parseParts(header, pointers, it),
            parseDeformers(header, pointers, it)
        )
    }

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
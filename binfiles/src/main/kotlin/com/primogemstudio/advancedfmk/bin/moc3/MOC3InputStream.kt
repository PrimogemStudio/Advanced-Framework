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
        val buff = ByteBuffer.allocate(4)
        buff.putInt(readInt())
        buff.order(if (be) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN)
        return buff.getInt(0)
    }
    fun parseFloat(be: Boolean): Float {
        val buff = ByteBuffer.allocate(4)
        buff.putFloat(readFloat())
        buff.order(if (be) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN)
        return buff.getFloat(0)
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
       seekTo(pointers.partOffset.keyformBindingSourceIndicesOffset)

        return Array(counts.parts) { parseInt(header.bigEndian) }
    }

    fun parsePartKeyframeSourcesBeginIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.partOffset.keyformSourcesBeginIndicesOffset)

        return Array(counts.parts) { parseInt(header.bigEndian) }
    }

    fun parsePartKeyframeSourcesCount(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.partOffset.keyformSourcesCountOffset)

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

    fun parseWarpDeformersKeyformBindingSourcesIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.warpDeformersOffset.keyformBindingSourceIndicesOffset)

        return Array(counts.warpDeformers) { parseInt(header.bigEndian) }
    }

    fun parseWarpDeformersKeyformSourcesBeginIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.warpDeformersOffset.keyformSourcesBeginIndicesOffset)

        return Array(counts.warpDeformers) { parseInt(header.bigEndian) }
    }

    fun parseWarpDeformersKeyformSourcesCounts(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.warpDeformersOffset.keyformSourcesCountOffset)

        return Array(counts.warpDeformers) { parseInt(header.bigEndian) }
    }

    fun parseWarpDeformersVertexCounts(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.warpDeformersOffset.vertexCountsOffset)

        return Array(counts.warpDeformers) { parseInt(header.bigEndian) }
    }

    fun parseWarpDeformersRows(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.warpDeformersOffset.rowsOffset)

        return Array(counts.warpDeformers) { parseInt(header.bigEndian) }
    }

    fun parseWarpDeformersColumns(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.warpDeformersOffset.columnsOffset)

        return Array(counts.warpDeformers) { parseInt(header.bigEndian) }
    }

    fun parseRotationDeformersKeyformBindingSourcesIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.rotateDeformersOffset.keyformBindingSourceIndicesOffset)

        return Array(counts.rotationDeformers) { parseInt(header.bigEndian) }
    }

    fun parseRotationDeformersKeyformSourcesBeginIndices(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.rotateDeformersOffset.keyformSourcesBeginIndicesOffset)

        return Array(counts.rotationDeformers) { parseInt(header.bigEndian) }
    }

    fun parseRotationDeformersKeyformSourcesCounts(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Int> {
        seekTo(pointers.rotateDeformersOffset.keyformSourcesCountOffset)

        return Array(counts.rotationDeformers) { parseInt(header.bigEndian) }
    }

    fun parseRotationDeformersBaseAngles(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<Float> {
        seekTo(pointers.rotateDeformersOffset.baseAngleOffset)

        return Array(counts.rotationDeformers) { parseFloat(header.bigEndian) }
    }

    fun parseRotationDeformers(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): MOC3RotationDeformers {
        return MOC3RotationDeformers(
            parseRotationDeformersKeyformBindingSourcesIndices(header, pointers, counts),
            parseRotationDeformersKeyformSourcesBeginIndices(header, pointers, counts) ,
            parseRotationDeformersKeyformSourcesCounts(header, pointers, counts),
            parseRotationDeformersBaseAngles(header, pointers, counts)
        )
    }

    fun parseWarpDeformers(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): MOC3WarpDeformers {
        return MOC3WarpDeformers(
            parseWarpDeformersKeyformBindingSourcesIndices(header, pointers, counts),
            parseWarpDeformersKeyformSourcesBeginIndices(header, pointers, counts),
            parseWarpDeformersKeyformSourcesCounts(header, pointers, counts),
            parseWarpDeformersVertexCounts(header, pointers, counts),
            parseWarpDeformersRows(header, pointers, counts),
            parseWarpDeformersColumns(header, pointers, counts)
        )
    }

    fun parseArtMeshesID(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<String> {
        seekTo(pointers.artMeshesOffset.idOffset)

        return Array(counts.artMeshes) { String(readNBytes(64)) }
    }

    fun parseArtMeshesDrawableFlags(header: MOC3Header, pointers: MOC3PointerMap, counts: MOC3CountInfoTableData): Array<MOC3ArtMeshesDrawableFlags> {
        seekTo(pointers.artMeshesOffset.drawableFlagsOffset)

        return Array(counts.artMeshes) {
            parseInt(header.bigEndian).let {
                MOC3ArtMeshesDrawableFlags(
                    if (header.bigEndian) it.shr(6).toByte() else it.and(0b00000011).toByte(),
                    if (header.bigEndian) it.shr(5).and(0b1) == 1 else it.shr(2).and(0b000001) == 1,
                    if (header.bigEndian) it.shr(4).and(0b1) == 1 else it.shr(3).and(0b000001) == 1,
                )
            }
        }
    }

    fun parseData(header: MOC3Header, pointers: MOC3PointerMap): MOC3Data = parseCountInfoTableData(header, pointers).let { counts ->
        MOC3Data(
            counts,
            parseCanvasInfo(header, pointers),
            parseParts(header, pointers, counts),
            parseDeformers(header, pointers, counts),
            parseWarpDeformers(header, pointers, counts),
            parseRotationDeformers(header, pointers, counts)
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
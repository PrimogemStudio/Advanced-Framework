package com.primogemstudio.advancedfmk.bin.moc3

data class MOC3Header(
    var magic: String,
    var version: Version?,
    var bigEndian: Boolean
) {
    enum class Version(
        val flag: Int
    ) {
        V3_00_00(1),
        V3_03_00(2),
        V4_00_00(3),
        V4_02_00(4),
        V5_00_00(5);

        companion object {
            fun get(b: Byte): Version? {
                return Version.entries.find { it.flag == b.toInt() }
            }
        }
    }
}

data class MOC3PartPointerMap(
    var idOffset: Int,
    var keyformBindingSourceIndicesOffset: Int,
    var keyformSourcesBeginIndicesOffset: Int,
    var keyformSourcesCountOffset: Int,
    var visibleOffset: Int,
    var enabledOffset: Int,
    var parentPartIndicesOffset: Int,
    var runtimeSpace0Offset: Int,
)

data class MOC3DeformersPointerMap(
    var idOffset: Int,
    var keyformBindingSourceIndicesOffset: Int,
    var visibleOffset: Int,
    var enabledOffset: Int,
    var parentPartIndicesOffset: Int,
    var parentDeformerIndicesOffset: Int,
    var typesIndicesOffset: Int,
    var specificSourcesIndicesOffset: Int
)

data class MOC3WarpDeformersPointerMap(
    var keyformBindingSourceIndicesOffset: Int,
    var keyformSourcesBeginIndicesOffset: Int,
    var keyformSourcesCountOffset: Int,
    var vertexCountsOffset: Int,
    var rowsOffset: Int,
    var columnsOffset: Int
)

data class MOC3RotateDeformersPointerMap(
    var keyformBindingSourceIndicesOffset: Int,
    var keyformSourcesBeginIndicesOffset: Int,
    var keyformSourcesCountOffset: Int,
    var baseAngleOffset: Int
)

data class MOC3ArtMeshesPointerMap(
    var runtimeSpace0Offset: Int,
    var runtimeSpace1Offset: Int,
    var runtimeSpace20Offset: Int,
    var runtimeSpace3Offset: Int,
    var idOffset: Int,
    var keyformBindingSourceIndicesOffset: Int,
    var keyformSourcesBeginIndicesOffset: Int,
    var keyformSourcesCountOffset: Int,
    var visibleOffset: Int,
    var enabledOffset: Int,
    var parentPartIndicesOffset: Int,
    var parentDeformerIndicesOffset: Int,
    var textureNumberOffset: Int,
    var drawableFlagsOffset: Int,
    var vertexCountsOffset: Int,
    var uvSourcesBeginOffset: Int,
    var positionIndexSourcesBeginIndicesOffset: Int,
    var positionIndexSourcesConutsOffset: Int,
    var drawableMasksSourcesBeginIndicesOffset: Int,
    var drawableMasksSourcesConutsOffset: Int
)

data class MOC3ParametersPointerMap(
    var runtimeSpace0Offset: Int,
    var idOffset: Int,
    var maxValuesOffset: Int,
    var minValuesOffset: Int,
    var defaultValuesOffset: Int,
    var repeatOffset: Int,
    var decimalPlacesOffset: Int,
    var parametersBindingSourcesBeginIndicesOffset: Int,
    var parametersBindingSourcesCountOffset: Int
)

data class MOC3WarpDeformerKeyformsPointerMap(
    var opacitiesOffset: Int,
    var keyformPositionSourcesBeginIndicesOffset: Int
)

data class MOC3RotateDeformerKeyformsPointerMap(
    var opacitiesOffset: Int,
    var anglesOffset: Int,
    var xOriginsOffset: Int,
    var yOriginsOffset: Int,
    var scalesOffset: Int,
    var reflectOnXOffset: Int,
    var reflectOnYOffset: Int
)

data class MOC3ArtMeshKeyformsPointerMap(
    var opacitiesOffset: Int,
    var drawOrdersOffset: Int,
    var keyformPositionSourcesBeginIndicesOffset: Int
)

data class MOC3KeyformsBindingsPointerMap(
    var parametersBindingIndexSourcesBeginIndicesOffset: Int,
    var parametersBindingIndexSourcesCountsOffset: Int
)

data class MOC3ParameterBindingsPointerMap(
    var keySourcesBeginIndicesOffset: Int,
    var keySourcesCountsOffset: Int
)

data class MOC3DrawOrderGroupsPointerMap(
    var objectSourcesBeginIndicesOffset: Int,
    var objectSourcesCountsOffset: Int,
    var objectSourcesTotalCountsOffset: Int,
    var maximumDrawOrdersOffset: Int,
    var minimumDrawOrdersOffset: Int
)

data class MOC3DrawOrderGroupObjectsPointerMap(
    var typesOffset: Int,
    var indicesOffset: Int,
    var selfIndicesOffset: Int
)

data class MOC3GluePointerMap(
    var runtimeSpace0Offset: Int,
    var idOffset: Int,
    var keyformBindingSourcesIndicesOffset: Int,
    var keyformSourcesBeginIndicesOffset: Int,
    var keyformBindingSourcesCountsOffset: Int,
    var artMeshIndicesAOffset: Int,
    var artMeshIndicesBOffset: Int,
    var infoSourcesBeginIndicesOffset: Int,
    var infoSourcesCountsOffset: Int
)

data class MOC3GlueInfoPointerMap(
    var weightsOffset: Int,
    var positionIndicesOffset: Int
)

data class MOC3ParameterExtensionsPointerMap(
    var runtimeSpace0Offset: Int,
    var keySourcesBeginIndicesOffset: Int,
    var keySourcesCountsOffset: Int
)

data class MOC3KeyformColorsMultiplyPointerMap(
    var redValuesOffset: Int,
    var greenValuesOffset: Int,
    var blueValuesOffset: Int,
)

data class MOC3KeyformColorsScreenPointerMap(
    var redValuesOffset: Int,
    var greenValuesOffset: Int,
    var blueValuesOffset: Int,
)

data class MOC3ParametersV42PointerMap(
    var typesOffset: Int,
    var blendShapeParameterBindingSourcesBeginIndicesOffset: Int,
    var blendShapeParameterBindingSourcesCountsOffset: Int
)

data class MOC3BlendShapeParameterBindingsPointerMap(
    var keySourcesBeginIndicesOffset: Int,
    var keySourcesCountsOffset: Int,
    var baseKeyIndicesOffset: Int
)

data class MOC3BlendShapeKeyformBindingsPointerMap(
    var parameterBindingSourcesIndicesOffset: Int,
    var keyformSourcesBlendShapeIndicesOffset: Int,
    var keyformSourcesBlendShapeCountsOffset: Int,
    var blendShapeConstraintIndexSourcesBeginIndicesOffset: Int,
    var blendShapeConstraintIndexSourcesCounts: Int
)

data class MOC3BlendShapesWarpDeformersPointerMap(
    var targetIndicesOffset: Int,
    var blendShapeKeyformBindingSourcesBeginIndicesOffset: Int,
    var blendShapeKeyformBindingSourcesCounts: Int
)

data class MOC3BlendShapesArtMeshesPointerMap(
    var targetIndicesOffset: Int,
    var blendShapeKeyformBindingSourcesBeginIndicesOffset: Int,
    var blendShapeKeyformBindingSourcesCounts: Int
)

data class MOC3BlendShapesConstraintsPointerMap(
    var parametersIndicesOffset: Int,
    var blendShapeConstraintValueSourcesBeginIndicesOffset: Int,
    var blendShapeConstraintValueSourcesCounts: Int
)

data class MOC3BlendShapesConstraintValuesPointerMap(
    var keysOffset: Int,
    var valuesOffset: Int
)

data class MOC3WarpDeformerKeyformsV50PointerMap(
    var keyformMultiplyColorSourcesBeginIndicesOffset: Int,
    var keyformScreenColorSourcesBeginIndicesOffset: Int
)

data class MOC3RotateDeformerKeyformsV50PointerMap(
    var keyformMultiplyColorSourcesBeginIndicesOffset: Int,
    var keyformScreenColorSourcesBeginIndicesOffset: Int
)

data class MOC3ArtMeshKeyformsV50PointerMap(
    var keyformMultiplyColorSourcesBeginIndicesOffset: Int,
    var keyformScreenColorSourcesBeginIndicesOffset: Int
)

data class MOC3BlendShapesPartsPointerMap(
    var targetIndicesOffset: Int,
    var blendShapeKeyformBindingSourceBeginIndicesOffset: Int,
    var blendShapeKeyformBindingSourceCounts: Int
)

data class MOC3BlendShapesRotateDeformersPointerMap(
    var targetIndicesOffset: Int,
    var blendShapeKeyformBindingSourceBeginIndicesOffset: Int,
    var blendShapeKeyformBindingSourceCounts: Int
)

data class MOC3BlendShapesGluePointerMap(
    var targetIndicesOffset: Int,
    var blendShapeKeyformBindingSourceBeginIndicesOffset: Int,
    var blendShapeKeyformBindingSourceCounts: Int
)

data class MOC3PointerMap(
    var countInfoOffset: Int,
    var canvasInfoOffset: Int,
    var runtimeDataOffset: Int,
    var partOffset: MOC3PartPointerMap,
    var deformersOffset: MOC3DeformersPointerMap,
    var warpDeformersOffset: MOC3WarpDeformersPointerMap,
    var rotateDeformersOffset: MOC3RotateDeformersPointerMap,
    var artMeshesOffset: MOC3ArtMeshesPointerMap,
    var parametersOffset: MOC3ParametersPointerMap,
    var partKeyframesDrawOrdersOffset: Int,
    var warpDeformerKeyformsOffset: MOC3WarpDeformerKeyformsPointerMap,
    var rotateDeformerKeyformsOffset: MOC3RotateDeformerKeyformsPointerMap,
    var artMeshKeyformsOffset: MOC3ArtMeshKeyformsPointerMap,
    var keyformsPositionsCoordinatesOffset: Int,
    var parameterBindingIndicesBindingSourceIndicesOffset: Int,
    var keyformsBindingsOffset: MOC3KeyformsBindingsPointerMap,
    var parameterBindingsOffset: MOC3ParameterBindingsPointerMap,
    var keyValuesOffset: Int,
    var uvOffset: Int,
    var positionIndicesOffset: Int,
    var drawableMasksAstMeshSourcesIndicesOffset: Int,
    var drawOrderGroupsOffset: MOC3DrawOrderGroupsPointerMap,
    var drawOrderGroupObjectsOffset: MOC3DrawOrderGroupObjectsPointerMap,
    var glueOffset: MOC3GluePointerMap,
    var glueInfoOffset: MOC3GlueInfoPointerMap,
    var glueKeyformsIntensitiesOffset: Int,
    // >= v3.03
    var warpDeformerKeyformsV33QuadSourceOffset: Int,
    // >= v4.02
    var parameterExtensionOffset: MOC3ParameterExtensionsPointerMap,
    var warpDeformerKeyformsV42KeyformColorColorSourcesBeginIndicesOffset: Int,
    var rotateDeformersV42KeyformColorSourcesBeginIndicesOffset: Int,
    var artMeshesV42KeyformColorSourcesBeginIndicesOffset: Int,
    var keyformColorsMultiplyOffset: MOC3KeyformColorsMultiplyPointerMap,
    var keyformColorsScreenOffset: MOC3KeyformColorsScreenPointerMap,
    var parametersV42Offset: MOC3ParametersV42PointerMap,
    var blendShapeParameterBindingsOffset: MOC3BlendShapeParameterBindingsPointerMap,
    var blendShapeKeyformBindingsOffset: MOC3BlendShapeKeyformBindingsPointerMap,
    var blendShapesWarpDeformersOffset: MOC3BlendShapesWarpDeformersPointerMap,
    var blendShapesArtMeshesOffset: MOC3BlendShapesArtMeshesPointerMap,
    var blendShapeConstraintSourcesIndicesOffset: Int,
    var blendShapeConstraintOffset: MOC3BlendShapesConstraintsPointerMap,
    var blendShapeConstraintValueOffset: MOC3BlendShapesConstraintValuesPointerMap,
    // >= v5.00
    var warpDeformerKeyformsV50Offset: MOC3WarpDeformerKeyformsV50PointerMap,
    var rotateDeformerKeyformsV50Offset: MOC3RotateDeformerKeyformsV50PointerMap,
    var artMeshDeformerKeyformsV50Offset: MOC3ArtMeshKeyformsV50PointerMap,
    var blendShapesParts: MOC3BlendShapesPartsPointerMap,
    var blendShapesRotateDeformersOffset: MOC3BlendShapesRotateDeformersPointerMap,
    var blendShapesGlueOffset: MOC3BlendShapesGluePointerMap
)

data class MOC3CountInfoTableData(
    var parts: Int,
    var deformers: Int,
    var warpDeformers: Int,
    var rotationDeformers: Int,
    var artMeshes: Int,
    var parameters: Int,
    var partKeyforms: Int,
    var warpDeformerKeyforms: Int,
    var rotationDeformerKeyforms: Int,
    var artMeshKeyforms: Int,
    var keyformPositions: Int,
    var parameterBindingIndices: Int,
    var keyformBindings: Int,
    var parameterBindings: Int,
    var keys: Int,
    var uvs: Int,
    var positionIndices: Int,
    var drawableMasks: Int,
    var drawOrderGroups: Int,
    var drawOrderGroupObjects: Int,
    var glue: Int,
    var glueInfo: Int,
    var glueKeyforms: Int,
    // >= v4.02
    var keyformColorsMultiply: Int,
    var blendShapeParameterBindings: Int,
    var blendShapeKeyformBindings: Int,
    var blendShapesWarpDeformers: Int,
    var blendShapeConstraintIndices: Int,
    var blendShapeConstraints: Int,
    var blendShapeConstraintValues: Int,
    // >= v5.00
    var blendShapesParts: Int,
    var blendShapesRotationDeformers: Int,
    var blendShapesGlues: Int
)

data class MOC3CanvasInfo(
    var pixelsPerUnit: Int,
    var xOrigin: Int,
    var yOrigin: Int,
    var width: Int,
    var height: Int,
    var flags: Int
)

data class MOC3Parts(
    var ids: Array<String>,
    var keyformBindingSourceIndices: Array<Int>,
    var keyformSourcesBeginIndices: Array<Int>,
    var keyformSourcesContent: Array<Int>,
    var visible: Array<Boolean>,
    var enabled: Array<Boolean>,
    var parentPartIndices: Array<Int>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MOC3Parts

        if (!ids.contentEquals(other.ids)) return false
        if (!keyformBindingSourceIndices.contentEquals(other.keyformBindingSourceIndices)) return false
        if (!keyformSourcesBeginIndices.contentEquals(other.keyformSourcesBeginIndices)) return false
        if (!keyformSourcesContent.contentEquals(other.keyformSourcesContent)) return false
        if (!visible.contentEquals(other.visible)) return false
        if (!enabled.contentEquals(other.enabled)) return false
        if (!parentPartIndices.contentEquals(other.parentPartIndices)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ids.contentHashCode()
        result = 31 * result + keyformBindingSourceIndices.contentHashCode()
        result = 31 * result + keyformSourcesBeginIndices.contentHashCode()
        result = 31 * result + keyformSourcesContent.contentHashCode()
        result = 31 * result + visible.contentHashCode()
        result = 31 * result + enabled.contentHashCode()
        result = 31 * result + parentPartIndices.contentHashCode()
        return result
    }
}

enum class MOC3DeformerType { WARP, ROTATE }

data class MOC3Deformers(
    var ids: Array<String>,
    var keyformBindingSourcesIndices: Array<Int>,
    var visible: Array<Boolean>,
    var enabled: Array<Boolean>,
    var parentPartIndices: Array<Int>,
    var parentDeformerIndices: Array<Int>,
    var types: Array<MOC3DeformerType>,
    var specificSourceIndices: Array<Int>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MOC3Deformers

        if (!ids.contentEquals(other.ids)) return false
        if (!keyformBindingSourcesIndices.contentEquals(other.keyformBindingSourcesIndices)) return false
        if (!visible.contentEquals(other.visible)) return false
        if (!enabled.contentEquals(other.enabled)) return false
        if (!parentPartIndices.contentEquals(other.parentPartIndices)) return false
        if (!parentDeformerIndices.contentEquals(other.parentDeformerIndices)) return false
        if (!types.contentEquals(other.types)) return false
        if (!specificSourceIndices.contentEquals(other.specificSourceIndices)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ids.contentHashCode()
        result = 31 * result + keyformBindingSourcesIndices.contentHashCode()
        result = 31 * result + visible.contentHashCode()
        result = 31 * result + enabled.contentHashCode()
        result = 31 * result + parentPartIndices.contentHashCode()
        result = 31 * result + parentDeformerIndices.contentHashCode()
        result = 31 * result + types.contentHashCode()
        result = 31 * result + specificSourceIndices.contentHashCode()
        return result
    }
}

data class MOC3WarpDeformers(
    var keyformBindingSourcesIndices: Array<Int>,
    var keyformSourcesBeginIndices: Array<Int>,
    var keyformSourcesCounts: Array<Int>,
    var vertexCounts: Array<Int>,
    var rows: Array<Int>,
    var columns: Array<Int>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MOC3WarpDeformers

        if (!keyformBindingSourcesIndices.contentEquals(other.keyformBindingSourcesIndices)) return false
        if (!keyformSourcesBeginIndices.contentEquals(other.keyformSourcesBeginIndices)) return false
        if (!keyformSourcesCounts.contentEquals(other.keyformSourcesCounts)) return false
        if (!vertexCounts.contentEquals(other.vertexCounts)) return false
        if (!rows.contentEquals(other.rows)) return false
        if (!columns.contentEquals(other.columns)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = keyformBindingSourcesIndices.contentHashCode()
        result = 31 * result + keyformSourcesBeginIndices.contentHashCode()
        result = 31 * result + keyformSourcesCounts.contentHashCode()
        result = 31 * result + vertexCounts.contentHashCode()
        result = 31 * result + rows.contentHashCode()
        result = 31 * result + columns.contentHashCode()
        return result
    }
}

data class MOC3RotationDeformers(
    var keyformBindingSourcesIndices: Array<Int>,
    var keyformSourcesBeginIndices: Array<Int>,
    var keyformSourcesCounts: Array<Int>,
    var baseAngles: Array<Float>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MOC3RotationDeformers

        if (!keyformBindingSourcesIndices.contentEquals(other.keyformBindingSourcesIndices)) return false
        if (!keyformSourcesBeginIndices.contentEquals(other.keyformSourcesBeginIndices)) return false
        if (!keyformSourcesCounts.contentEquals(other.keyformSourcesCounts)) return false
        if (!baseAngles.contentEquals(other.baseAngles)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = keyformBindingSourcesIndices.contentHashCode()
        result = 31 * result + keyformSourcesBeginIndices.contentHashCode()
        result = 31 * result + keyformSourcesCounts.contentHashCode()
        result = 31 * result + baseAngles.contentHashCode()
        return result
    }
}

data class MOC3ArtMeshesDrawableFlags(
    var blendMode: Byte,
    var isDoubleSided: Boolean,
    var isInverted: Boolean
)

data class MOC3ArtMeshes(
    var ids: Array<String>,
    var keyformBindingSourcesIndices: Array<Int>,
    var keyformSourcesBeginIndices: Array<Int>,
    var keyformSourcesCounts: Array<Int>,
    var visible: Array<Boolean>,
    var enabled: Array<Boolean>,
    var parentPartIndices: Array<Int>,
    var parentDeformerIndices: Array<Int>,
    var drawableFlags: Array<MOC3ArtMeshesDrawableFlags>,
    var vertexCounts: Array<Int>,
    var uvSourcesBeginIndices: Array<Int>,
    var positionIndexSourecsBeginIndices: Array<Int>,
    var positionIndexSourcesCounts: Array<Int>,
    var drawableMaskSourcesBeginIndices: Array<Int>,
    var drawableMaskSourcesCounts: Array<Int>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MOC3ArtMeshes

        if (!ids.contentEquals(other.ids)) return false
        if (!keyformBindingSourcesIndices.contentEquals(other.keyformBindingSourcesIndices)) return false
        if (!keyformSourcesBeginIndices.contentEquals(other.keyformSourcesBeginIndices)) return false
        if (!keyformSourcesCounts.contentEquals(other.keyformSourcesCounts)) return false
        if (!visible.contentEquals(other.visible)) return false
        if (!enabled.contentEquals(other.enabled)) return false
        if (!parentPartIndices.contentEquals(other.parentPartIndices)) return false
        if (!parentDeformerIndices.contentEquals(other.parentDeformerIndices)) return false
        if (!drawableFlags.contentEquals(other.drawableFlags)) return false
        if (!vertexCounts.contentEquals(other.vertexCounts)) return false
        if (!uvSourcesBeginIndices.contentEquals(other.uvSourcesBeginIndices)) return false
        if (!positionIndexSourecsBeginIndices.contentEquals(other.positionIndexSourecsBeginIndices)) return false
        if (!positionIndexSourcesCounts.contentEquals(other.positionIndexSourcesCounts)) return false
        if (!drawableMaskSourcesBeginIndices.contentEquals(other.drawableMaskSourcesBeginIndices)) return false
        if (!drawableMaskSourcesCounts.contentEquals(other.drawableMaskSourcesCounts)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ids.contentHashCode()
        result = 31 * result + keyformBindingSourcesIndices.contentHashCode()
        result = 31 * result + keyformSourcesBeginIndices.contentHashCode()
        result = 31 * result + keyformSourcesCounts.contentHashCode()
        result = 31 * result + visible.contentHashCode()
        result = 31 * result + enabled.contentHashCode()
        result = 31 * result + parentPartIndices.contentHashCode()
        result = 31 * result + parentDeformerIndices.contentHashCode()
        result = 31 * result + drawableFlags.contentHashCode()
        result = 31 * result + vertexCounts.contentHashCode()
        result = 31 * result + uvSourcesBeginIndices.contentHashCode()
        result = 31 * result + positionIndexSourecsBeginIndices.contentHashCode()
        result = 31 * result + positionIndexSourcesCounts.contentHashCode()
        result = 31 * result + drawableMaskSourcesBeginIndices.contentHashCode()
        result = 31 * result + drawableMaskSourcesCounts.contentHashCode()
        return result
    }
}

data class MOC3Data(
    var countInfoTable: MOC3CountInfoTableData,
    var canvasInfo: MOC3CanvasInfo,
    var parts: MOC3Parts,
    var deformers: MOC3Deformers,
    var warpDeformers: MOC3WarpDeformers,
    var rotationDeformers: MOC3RotationDeformers
)

data class MOC3Model(
    var header: MOC3Header,
    var pointers: MOC3PointerMap,
    var data: MOC3Data
)
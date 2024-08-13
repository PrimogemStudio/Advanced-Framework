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

data class MOC3PointerMap(
    var countInfoOffset: Int,
    var canvasInfoOffset: Int,
    var runtimeDataOffset: Int,
    var partOffset: MOC3PartPointerMap
)

data class MOC3PartPointerMap(
    var idOffset: Int,
    var keyframeBindingSourceIndicesOffset: Int,
    var keyframeSourcesBeginIndicesOffset: Int,
    var keyframeSourcesContentOffset: Int,
    var visibleOffset: Int,
    var enabledOffset: Int,
    var parentPartIndicesOffset: Int
)

data class MOC3Model(
    var header: MOC3Header,
    val pointers: MOC3PointerMap
)
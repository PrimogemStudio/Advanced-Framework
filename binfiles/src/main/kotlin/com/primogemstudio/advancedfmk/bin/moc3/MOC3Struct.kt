package com.primogemstudio.advancedfmk.bin.moc3

data class MOC3Header(
    val magic: String,
    val version: Version?,
    val bigEndian: Boolean
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
package com.primogemstudio.mmdbase.io

class PMXDisplayFrame {
    var m_name = ""
    var m_englishName = ""
    var m_flag = FrameType.DefaultFrame
    var m_targets = emptyArray<Target>()
}

enum class FrameType {
    DefaultFrame,
    SpecialFrame,
}

enum class TargetType {
    BoneIndex,
    MorphIndex,
}
class Target {
    var m_type = TargetType.BoneIndex
    var m_index = 0
}
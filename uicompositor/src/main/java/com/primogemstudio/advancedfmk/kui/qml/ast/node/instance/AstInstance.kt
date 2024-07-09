package com.primogemstudio.advancedfmk.kui.qml.ast.node.instance

import com.primogemstudio.advancedfmk.kui.qml.ast.node.AstObject

data class AstInstance(
    val type: String = "",
    val init: AstInstanceInitGroup = AstInstanceInitGroup()
) : AstObject
package com.primogemstudio.advancedfmk.kui.qml.ast.node.instance

import com.primogemstudio.advancedfmk.kui.qml.ast.node.AstObject

data class AstInstanceInitGroup(
    val inits: List<AstInstanceInit> = listOf()
) : AstObject
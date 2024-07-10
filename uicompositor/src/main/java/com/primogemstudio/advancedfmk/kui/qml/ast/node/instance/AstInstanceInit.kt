package com.primogemstudio.advancedfmk.kui.qml.ast.node.instance

import com.primogemstudio.advancedfmk.kui.qml.ast.node.AstObject
import com.primogemstudio.advancedfmk.kui.qml.ast.node.expr.AstExprBase

data class AstInstanceInit(
    val name: String = "",
    val type: String? = null,
    val expr: AstExprBase? = null
) : AstObject
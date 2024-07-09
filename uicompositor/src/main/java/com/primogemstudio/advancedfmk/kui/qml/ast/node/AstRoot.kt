package com.primogemstudio.advancedfmk.kui.qml.ast.node

import com.primogemstudio.advancedfmk.kui.qml.ast.node.imp.AstImports
import com.primogemstudio.advancedfmk.kui.qml.ast.node.instance.AstInstance

data class AstRoot(
    val imports: AstImports = AstImports(),
    var root: AstInstance = AstInstance()
) : AstObject
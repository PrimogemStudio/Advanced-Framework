package com.primogemstudio.advancedfmk.kui.qml.ast.node.imp

import com.primogemstudio.advancedfmk.kui.qml.ast.node.AstObject

data class AstImport(
    val module: String,
    val version: Number,
    val name: String?
) : AstObject

data class AstImports(
    val imports: MutableList<AstImport> = mutableListOf()
) : AstObject
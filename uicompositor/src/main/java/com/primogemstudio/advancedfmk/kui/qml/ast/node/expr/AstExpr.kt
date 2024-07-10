package com.primogemstudio.advancedfmk.kui.qml.ast.node.expr

import com.primogemstudio.advancedfmk.kui.qml.ast.node.AstObject

enum class Assign {
    EQ,
    STAR_EQ,
    DIVIDE_EQ,
    REMAINDER_EQ,
    PLUS_EQ,
    MINUS_EQ,
    LLEQ,
    GGEQ,
    GGGEQ,
    AND_EQ,
    XOR_EQ,
    OR_EQ
}

abstract class AstExprBase : AstObject

abstract class AstExprPrimitive(
    open val arg: Any
) : AstExprBase()

class AstExprNumberPrimitive(
    override val arg: Number
) : AstExprPrimitive(arg)

class AstExprRefPrimitive(
    override val arg: String
) : AstExprPrimitive(arg)

class AstExprAssign(
    val left: AstExprBase?,
    val sign: Assign?,
    val right: AstExprBase?
) : AstExprBase()
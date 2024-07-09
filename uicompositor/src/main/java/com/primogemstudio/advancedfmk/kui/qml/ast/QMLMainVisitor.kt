package com.primogemstudio.advancedfmk.kui.qml.ast

import com.primogemstudio.advancedfmk.kui.qml.ast.node.AstNop
import com.primogemstudio.advancedfmk.kui.qml.ast.node.AstObject
import com.primogemstudio.advancedfmk.kui.qml.ast.node.AstRoot
import com.primogemstudio.advancedfmk.kui.qml.ast.node.imp.AstImport
import com.primogemstudio.advancedfmk.kui.qml.ast.node.imp.AstImports
import com.primogemstudio.advancedfmk.kui.qml.ast.node.instance.AstInstance
import com.primogemstudio.advancedfmk.kui.qml.ast.node.instance.AstInstanceInit
import com.primogemstudio.advancedfmk.kui.qml.ast.node.instance.AstInstanceInitGroup
import com.primogemstudio.advancedfmk.kui.qml.parser.QMLParser
import com.primogemstudio.advancedfmk.kui.qml.parser.QMLVisitor
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor

class QMLMainVisitor : AbstractParseTreeVisitor<AstObject>(), QMLVisitor<AstObject> {
    override fun visitProgram(ctx: QMLParser.ProgramContext): AstRoot {
        val root = AstRoot(
            AstImports(ctx.import_().map { visitImport_(it) }.toMutableList()),
            visitRootMember(ctx.rootMember())
        )

        return root
    }

    override fun visitImport_(ctx: QMLParser.Import_Context): AstImport {
        return AstImport(
            ctx.importIdentifier().JsIdentifier().text,
            ctx.NumericLiteral().text.toDouble(),
            if (ctx.AS() != null) ctx.importAlias().JsIdentifier().text else null
        )
    }

    override fun visitImportIdentifier(ctx: QMLParser.ImportIdentifierContext): AstNop = AstNop()
    override fun visitImportAlias(ctx: QMLParser.ImportAliasContext): AstNop = AstNop()

    override fun visitRootMember(ctx: QMLParser.RootMemberContext): AstInstance {
        return visitObjectDefinition(ctx.objectDefinition())
    }

    override fun visitObjectDefinition(ctx: QMLParser.ObjectDefinitionContext): AstInstance {
        return AstInstance(
            ctx.JsIdentifier().text,
            visitObjectInitializer(ctx.objectInitializer())
        )
    }

    override fun visitQualifiedId(ctx: QMLParser.QualifiedIdContext): AstNop = AstNop()

    override fun visitObjectInitializer(ctx: QMLParser.ObjectInitializerContext): AstInstanceInitGroup {
        return AstInstanceInitGroup(
            ctx.objectMember().map { visitObjectMember(it) }
        )
    }

    override fun visitObjectMember(ctx: QMLParser.ObjectMemberContext): AstInstanceInit {
        return AstInstanceInit(
            ctx.qualifiedId(0).text
        )
    }

    override fun visitPropertyDeclaration(ctx: QMLParser.PropertyDeclarationContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitPropertyDeclarationAndAssignObjectDefinition(ctx: QMLParser.PropertyDeclarationAndAssignObjectDefinitionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitParameterList(ctx: QMLParser.ParameterListContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitPropertyType(ctx: QMLParser.PropertyTypeContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitArrayMemberList(ctx: QMLParser.ArrayMemberListContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitScriptStatement(ctx: QMLParser.ScriptStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitStatement(ctx: QMLParser.StatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitVariableStatement(ctx: QMLParser.VariableStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitVariableDeclarationKind(ctx: QMLParser.VariableDeclarationKindContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitVariableDeclarationList(ctx: QMLParser.VariableDeclarationListContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitVariableDeclaration(ctx: QMLParser.VariableDeclarationContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitInitialiser(ctx: QMLParser.InitialiserContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitDebuggerStatement(ctx: QMLParser.DebuggerStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitThrowStatement(ctx: QMLParser.ThrowStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitContinueStatement(ctx: QMLParser.ContinueStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitIterationStatement(ctx: QMLParser.IterationStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitVariableDeclarationListNotIn(ctx: QMLParser.VariableDeclarationListNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitVariableDeclarationNotIn(ctx: QMLParser.VariableDeclarationNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitInitialiserNotIn(ctx: QMLParser.InitialiserNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitExpressionNotIn(ctx: QMLParser.ExpressionNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitAssignmentExpressionNotIn(ctx: QMLParser.AssignmentExpressionNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitAssignmentOperator(ctx: QMLParser.AssignmentOperatorContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitConditionalExpressionNotIn(ctx: QMLParser.ConditionalExpressionNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitLogicalORExpressionNotIn(ctx: QMLParser.LogicalORExpressionNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitLogicalANDExpressionNotIn(ctx: QMLParser.LogicalANDExpressionNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitBitwiseORExpressionNotIn(ctx: QMLParser.BitwiseORExpressionNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitBitwiseXORExpressionNotIn(ctx: QMLParser.BitwiseXORExpressionNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitBitwiseANDExpressionNotIn(ctx: QMLParser.BitwiseANDExpressionNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitEqualityExpressionNotIn(ctx: QMLParser.EqualityExpressionNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitRelationalExpressionNotIn(ctx: QMLParser.RelationalExpressionNotInContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitLabelledStatement(ctx: QMLParser.LabelledStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitBreakStatement(ctx: QMLParser.BreakStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitReturnStatement(ctx: QMLParser.ReturnStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitTryStatement(ctx: QMLParser.TryStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitCatch_(ctx: QMLParser.Catch_Context): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitFinally_(ctx: QMLParser.Finally_Context): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitBlock(ctx: QMLParser.BlockContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitCaseBlock(ctx: QMLParser.CaseBlockContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitCaseClause(ctx: QMLParser.CaseClauseContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitDefaultClause(ctx: QMLParser.DefaultClauseContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitSwitchStatement(ctx: QMLParser.SwitchStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitWithStatement(ctx: QMLParser.WithStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitIfStatement(ctx: QMLParser.IfStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitStatementListOpt(ctx: QMLParser.StatementListOptContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitStatementList(ctx: QMLParser.StatementListContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitEmptyStatement(ctx: QMLParser.EmptyStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitExpressionStatement(ctx: QMLParser.ExpressionStatementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitExpression(ctx: QMLParser.ExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitAssignmentExpression(ctx: QMLParser.AssignmentExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitLeftHandSideExpression(ctx: QMLParser.LeftHandSideExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitCallExpression(ctx: QMLParser.CallExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitNewExpression(ctx: QMLParser.NewExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitMemberExpression(ctx: QMLParser.MemberExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitArgumentList(ctx: QMLParser.ArgumentListContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitPropertyIdentifier(ctx: QMLParser.PropertyIdentifierContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitFunctionExpression(ctx: QMLParser.FunctionExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitFormalParameterList(ctx: QMLParser.FormalParameterListContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitFunctionBody(ctx: QMLParser.FunctionBodyContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitSourceElement(ctx: QMLParser.SourceElementContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitFunctionDeclaration(ctx: QMLParser.FunctionDeclarationContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitPrimaryExpression(ctx: QMLParser.PrimaryExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitPropertyAssignmentListOpt(ctx: QMLParser.PropertyAssignmentListOptContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitPopertyAssignmentList(ctx: QMLParser.PopertyAssignmentListContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitPropertyAssignmentList(ctx: QMLParser.PropertyAssignmentListContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitPropertyAssignment(ctx: QMLParser.PropertyAssignmentContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitPropertyName(ctx: QMLParser.PropertyNameContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitElementList(ctx: QMLParser.ElementListContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitElision(ctx: QMLParser.ElisionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitConditionalExpression(ctx: QMLParser.ConditionalExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitLogicalORExpression(ctx: QMLParser.LogicalORExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitLogicalANDExpression(ctx: QMLParser.LogicalANDExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitBitwiseORExpression(ctx: QMLParser.BitwiseORExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitBitwiseXORExpression(ctx: QMLParser.BitwiseXORExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitBitwiseANDExpression(ctx: QMLParser.BitwiseANDExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitEqualityExpression(ctx: QMLParser.EqualityExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitRelationalExpression(ctx: QMLParser.RelationalExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitShiftExpression(ctx: QMLParser.ShiftExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitAdditiveExpression(ctx: QMLParser.AdditiveExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitMultiplicativeExpression(ctx: QMLParser.MultiplicativeExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitUnaryExpression(ctx: QMLParser.UnaryExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitPostfixExpression(ctx: QMLParser.PostfixExpressionContext): AstObject {
        TODO("Not yet implemented")
    }

    override fun visitReservedIdentifier(ctx: QMLParser.ReservedIdentifierContext): AstObject {
        TODO("Not yet implemented")
    }
}
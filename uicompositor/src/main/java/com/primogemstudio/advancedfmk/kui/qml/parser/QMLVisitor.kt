// Generated from /home/coder2/Advanced-Framework/uicompositor/src/main/resources/QML.g4 by ANTLR 4.13.1
package com.primogemstudio.advancedfmk.kui.qml.parser

import com.primogemstudio.advancedfmk.kui.qml.parser.QMLParser.*
import org.antlr.v4.runtime.tree.ParseTreeVisitor

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by [QMLParser].
 *
 * @param <T> The return type of the visit operation. Use [Void] for
 * operations with no return type.
</T> */
interface QMLVisitor<T> : ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by [QMLParser.program].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitProgram(ctx: ProgramContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.import_].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitImport_(ctx: Import_Context?): T

    /**
     * Visit a parse tree produced by [QMLParser.importIdentifier].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitImportIdentifier(ctx: ImportIdentifierContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.importAlias].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitImportAlias(ctx: ImportAliasContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.rootMember].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitRootMember(ctx: RootMemberContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.objectDefinition].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitObjectDefinition(ctx: ObjectDefinitionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.qualifiedId].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitQualifiedId(ctx: QualifiedIdContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.objectInitializer].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitObjectInitializer(ctx: ObjectInitializerContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.objectMember].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitObjectMember(ctx: ObjectMemberContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.propertyDeclaration].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPropertyDeclaration(ctx: PropertyDeclarationContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.propertyDeclarationAndAssignObjectDefinition].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPropertyDeclarationAndAssignObjectDefinition(ctx: PropertyDeclarationAndAssignObjectDefinitionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.parameterList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitParameterList(ctx: ParameterListContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.propertyType].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPropertyType(ctx: PropertyTypeContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.arrayMemberList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitArrayMemberList(ctx: ArrayMemberListContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.scriptStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitScriptStatement(ctx: ScriptStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.statement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitStatement(ctx: StatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.variableStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVariableStatement(ctx: VariableStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.variableDeclarationKind].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVariableDeclarationKind(ctx: VariableDeclarationKindContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.variableDeclarationList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVariableDeclarationList(ctx: VariableDeclarationListContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.variableDeclaration].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVariableDeclaration(ctx: VariableDeclarationContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.initialiser].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitInitialiser(ctx: InitialiserContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.debuggerStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitDebuggerStatement(ctx: DebuggerStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.throwStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitThrowStatement(ctx: ThrowStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.continueStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitContinueStatement(ctx: ContinueStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.iterationStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitIterationStatement(ctx: IterationStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.variableDeclarationListNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVariableDeclarationListNotIn(ctx: VariableDeclarationListNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.variableDeclarationNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVariableDeclarationNotIn(ctx: VariableDeclarationNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.initialiserNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitInitialiserNotIn(ctx: InitialiserNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.expressionNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitExpressionNotIn(ctx: ExpressionNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.assignmentExpressionNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitAssignmentExpressionNotIn(ctx: AssignmentExpressionNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.assignmentOperator].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitAssignmentOperator(ctx: AssignmentOperatorContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.conditionalExpressionNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitConditionalExpressionNotIn(ctx: ConditionalExpressionNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.logicalORExpressionNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLogicalORExpressionNotIn(ctx: LogicalORExpressionNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.logicalANDExpressionNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLogicalANDExpressionNotIn(ctx: LogicalANDExpressionNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.bitwiseORExpressionNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBitwiseORExpressionNotIn(ctx: BitwiseORExpressionNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.bitwiseXORExpressionNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBitwiseXORExpressionNotIn(ctx: BitwiseXORExpressionNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.bitwiseANDExpressionNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBitwiseANDExpressionNotIn(ctx: BitwiseANDExpressionNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.equalityExpressionNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitEqualityExpressionNotIn(ctx: EqualityExpressionNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.relationalExpressionNotIn].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitRelationalExpressionNotIn(ctx: RelationalExpressionNotInContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.labelledStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLabelledStatement(ctx: LabelledStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.breakStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBreakStatement(ctx: BreakStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.returnStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitReturnStatement(ctx: ReturnStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.tryStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitTryStatement(ctx: TryStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.catch_].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitCatch_(ctx: Catch_Context?): T

    /**
     * Visit a parse tree produced by [QMLParser.finally_].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFinally_(ctx: Finally_Context?): T

    /**
     * Visit a parse tree produced by [QMLParser.block].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBlock(ctx: BlockContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.caseBlock].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitCaseBlock(ctx: CaseBlockContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.caseClause].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitCaseClause(ctx: CaseClauseContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.defaultClause].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitDefaultClause(ctx: DefaultClauseContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.switchStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitSwitchStatement(ctx: SwitchStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.withStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitWithStatement(ctx: WithStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.ifStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitIfStatement(ctx: IfStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.statementListOpt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitStatementListOpt(ctx: StatementListOptContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.statementList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitStatementList(ctx: StatementListContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.emptyStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitEmptyStatement(ctx: EmptyStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.expressionStatement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitExpressionStatement(ctx: ExpressionStatementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.expression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitExpression(ctx: ExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.assignmentExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitAssignmentExpression(ctx: AssignmentExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.leftHandSideExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLeftHandSideExpression(ctx: LeftHandSideExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.callExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitCallExpression(ctx: CallExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.newExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitNewExpression(ctx: NewExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.memberExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitMemberExpression(ctx: MemberExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.argumentList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitArgumentList(ctx: ArgumentListContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.propertyIdentifier].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPropertyIdentifier(ctx: PropertyIdentifierContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.functionExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFunctionExpression(ctx: FunctionExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.formalParameterList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFormalParameterList(ctx: FormalParameterListContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.functionBody].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFunctionBody(ctx: FunctionBodyContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.sourceElement].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitSourceElement(ctx: SourceElementContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.functionDeclaration].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFunctionDeclaration(ctx: FunctionDeclarationContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.primaryExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPrimaryExpression(ctx: PrimaryExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.propertyAssignmentListOpt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPropertyAssignmentListOpt(ctx: PropertyAssignmentListOptContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.popertyAssignmentList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPopertyAssignmentList(ctx: PopertyAssignmentListContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.propertyAssignmentList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPropertyAssignmentList(ctx: PropertyAssignmentListContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.propertyAssignment].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPropertyAssignment(ctx: PropertyAssignmentContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.propertyName].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPropertyName(ctx: PropertyNameContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.elementList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitElementList(ctx: ElementListContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.elision].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitElision(ctx: ElisionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.conditionalExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitConditionalExpression(ctx: ConditionalExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.logicalORExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLogicalORExpression(ctx: LogicalORExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.logicalANDExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLogicalANDExpression(ctx: LogicalANDExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.bitwiseORExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBitwiseORExpression(ctx: BitwiseORExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.bitwiseXORExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBitwiseXORExpression(ctx: BitwiseXORExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.bitwiseANDExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBitwiseANDExpression(ctx: BitwiseANDExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.equalityExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitEqualityExpression(ctx: EqualityExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.relationalExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitRelationalExpression(ctx: RelationalExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.shiftExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitShiftExpression(ctx: ShiftExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.additiveExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitAdditiveExpression(ctx: AdditiveExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.multiplicativeExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitMultiplicativeExpression(ctx: MultiplicativeExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.unaryExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitUnaryExpression(ctx: UnaryExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.postfixExpression].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPostfixExpression(ctx: PostfixExpressionContext?): T

    /**
     * Visit a parse tree produced by [QMLParser.reservedIdentifier].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitReservedIdentifier(ctx: ReservedIdentifierContext?): T
}
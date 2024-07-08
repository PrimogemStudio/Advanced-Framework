// Generated from /home/coder2/Advanced-Framework/uicompositor/src/main/resources/QML.g4 by ANTLR 4.13.1
package com.primogemstudio.advancedfmk.kui.qml.parser

import com.primogemstudio.advancedfmk.kui.qml.parser.QMLParser.*
import org.antlr.v4.runtime.tree.ParseTreeListener

/**
 * This interface defines a complete listener for a parse tree produced by
 * [QMLParser].
 */
interface QMLListener : ParseTreeListener {
    /**
     * Enter a parse tree produced by [QMLParser.program].
     * @param ctx the parse tree
     */
    fun enterProgram(ctx: ProgramContext?)

    /**
     * Exit a parse tree produced by [QMLParser.program].
     * @param ctx the parse tree
     */
    fun exitProgram(ctx: ProgramContext?)

    /**
     * Enter a parse tree produced by [QMLParser.import_].
     * @param ctx the parse tree
     */
    fun enterImport_(ctx: Import_Context?)

    /**
     * Exit a parse tree produced by [QMLParser.import_].
     * @param ctx the parse tree
     */
    fun exitImport_(ctx: Import_Context?)

    /**
     * Enter a parse tree produced by [QMLParser.importIdentifier].
     * @param ctx the parse tree
     */
    fun enterImportIdentifier(ctx: ImportIdentifierContext?)

    /**
     * Exit a parse tree produced by [QMLParser.importIdentifier].
     * @param ctx the parse tree
     */
    fun exitImportIdentifier(ctx: ImportIdentifierContext?)

    /**
     * Enter a parse tree produced by [QMLParser.importAlias].
     * @param ctx the parse tree
     */
    fun enterImportAlias(ctx: ImportAliasContext?)

    /**
     * Exit a parse tree produced by [QMLParser.importAlias].
     * @param ctx the parse tree
     */
    fun exitImportAlias(ctx: ImportAliasContext?)

    /**
     * Enter a parse tree produced by [QMLParser.rootMember].
     * @param ctx the parse tree
     */
    fun enterRootMember(ctx: RootMemberContext?)

    /**
     * Exit a parse tree produced by [QMLParser.rootMember].
     * @param ctx the parse tree
     */
    fun exitRootMember(ctx: RootMemberContext?)

    /**
     * Enter a parse tree produced by [QMLParser.objectDefinition].
     * @param ctx the parse tree
     */
    fun enterObjectDefinition(ctx: ObjectDefinitionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.objectDefinition].
     * @param ctx the parse tree
     */
    fun exitObjectDefinition(ctx: ObjectDefinitionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.qualifiedId].
     * @param ctx the parse tree
     */
    fun enterQualifiedId(ctx: QualifiedIdContext?)

    /**
     * Exit a parse tree produced by [QMLParser.qualifiedId].
     * @param ctx the parse tree
     */
    fun exitQualifiedId(ctx: QualifiedIdContext?)

    /**
     * Enter a parse tree produced by [QMLParser.objectInitializer].
     * @param ctx the parse tree
     */
    fun enterObjectInitializer(ctx: ObjectInitializerContext?)

    /**
     * Exit a parse tree produced by [QMLParser.objectInitializer].
     * @param ctx the parse tree
     */
    fun exitObjectInitializer(ctx: ObjectInitializerContext?)

    /**
     * Enter a parse tree produced by [QMLParser.objectMember].
     * @param ctx the parse tree
     */
    fun enterObjectMember(ctx: ObjectMemberContext?)

    /**
     * Exit a parse tree produced by [QMLParser.objectMember].
     * @param ctx the parse tree
     */
    fun exitObjectMember(ctx: ObjectMemberContext?)

    /**
     * Enter a parse tree produced by [QMLParser.propertyDeclaration].
     * @param ctx the parse tree
     */
    fun enterPropertyDeclaration(ctx: PropertyDeclarationContext?)

    /**
     * Exit a parse tree produced by [QMLParser.propertyDeclaration].
     * @param ctx the parse tree
     */
    fun exitPropertyDeclaration(ctx: PropertyDeclarationContext?)

    /**
     * Enter a parse tree produced by [QMLParser.propertyDeclarationAndAssignObjectDefinition].
     * @param ctx the parse tree
     */
    fun enterPropertyDeclarationAndAssignObjectDefinition(ctx: PropertyDeclarationAndAssignObjectDefinitionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.propertyDeclarationAndAssignObjectDefinition].
     * @param ctx the parse tree
     */
    fun exitPropertyDeclarationAndAssignObjectDefinition(ctx: PropertyDeclarationAndAssignObjectDefinitionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.parameterList].
     * @param ctx the parse tree
     */
    fun enterParameterList(ctx: ParameterListContext?)

    /**
     * Exit a parse tree produced by [QMLParser.parameterList].
     * @param ctx the parse tree
     */
    fun exitParameterList(ctx: ParameterListContext?)

    /**
     * Enter a parse tree produced by [QMLParser.propertyType].
     * @param ctx the parse tree
     */
    fun enterPropertyType(ctx: PropertyTypeContext?)

    /**
     * Exit a parse tree produced by [QMLParser.propertyType].
     * @param ctx the parse tree
     */
    fun exitPropertyType(ctx: PropertyTypeContext?)

    /**
     * Enter a parse tree produced by [QMLParser.arrayMemberList].
     * @param ctx the parse tree
     */
    fun enterArrayMemberList(ctx: ArrayMemberListContext?)

    /**
     * Exit a parse tree produced by [QMLParser.arrayMemberList].
     * @param ctx the parse tree
     */
    fun exitArrayMemberList(ctx: ArrayMemberListContext?)

    /**
     * Enter a parse tree produced by [QMLParser.scriptStatement].
     * @param ctx the parse tree
     */
    fun enterScriptStatement(ctx: ScriptStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.scriptStatement].
     * @param ctx the parse tree
     */
    fun exitScriptStatement(ctx: ScriptStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.statement].
     * @param ctx the parse tree
     */
    fun enterStatement(ctx: StatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.statement].
     * @param ctx the parse tree
     */
    fun exitStatement(ctx: StatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.variableStatement].
     * @param ctx the parse tree
     */
    fun enterVariableStatement(ctx: VariableStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.variableStatement].
     * @param ctx the parse tree
     */
    fun exitVariableStatement(ctx: VariableStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.variableDeclarationKind].
     * @param ctx the parse tree
     */
    fun enterVariableDeclarationKind(ctx: VariableDeclarationKindContext?)

    /**
     * Exit a parse tree produced by [QMLParser.variableDeclarationKind].
     * @param ctx the parse tree
     */
    fun exitVariableDeclarationKind(ctx: VariableDeclarationKindContext?)

    /**
     * Enter a parse tree produced by [QMLParser.variableDeclarationList].
     * @param ctx the parse tree
     */
    fun enterVariableDeclarationList(ctx: VariableDeclarationListContext?)

    /**
     * Exit a parse tree produced by [QMLParser.variableDeclarationList].
     * @param ctx the parse tree
     */
    fun exitVariableDeclarationList(ctx: VariableDeclarationListContext?)

    /**
     * Enter a parse tree produced by [QMLParser.variableDeclaration].
     * @param ctx the parse tree
     */
    fun enterVariableDeclaration(ctx: VariableDeclarationContext?)

    /**
     * Exit a parse tree produced by [QMLParser.variableDeclaration].
     * @param ctx the parse tree
     */
    fun exitVariableDeclaration(ctx: VariableDeclarationContext?)

    /**
     * Enter a parse tree produced by [QMLParser.initialiser].
     * @param ctx the parse tree
     */
    fun enterInitialiser(ctx: InitialiserContext?)

    /**
     * Exit a parse tree produced by [QMLParser.initialiser].
     * @param ctx the parse tree
     */
    fun exitInitialiser(ctx: InitialiserContext?)

    /**
     * Enter a parse tree produced by [QMLParser.debuggerStatement].
     * @param ctx the parse tree
     */
    fun enterDebuggerStatement(ctx: DebuggerStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.debuggerStatement].
     * @param ctx the parse tree
     */
    fun exitDebuggerStatement(ctx: DebuggerStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.throwStatement].
     * @param ctx the parse tree
     */
    fun enterThrowStatement(ctx: ThrowStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.throwStatement].
     * @param ctx the parse tree
     */
    fun exitThrowStatement(ctx: ThrowStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.continueStatement].
     * @param ctx the parse tree
     */
    fun enterContinueStatement(ctx: ContinueStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.continueStatement].
     * @param ctx the parse tree
     */
    fun exitContinueStatement(ctx: ContinueStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.iterationStatement].
     * @param ctx the parse tree
     */
    fun enterIterationStatement(ctx: IterationStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.iterationStatement].
     * @param ctx the parse tree
     */
    fun exitIterationStatement(ctx: IterationStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.variableDeclarationListNotIn].
     * @param ctx the parse tree
     */
    fun enterVariableDeclarationListNotIn(ctx: VariableDeclarationListNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.variableDeclarationListNotIn].
     * @param ctx the parse tree
     */
    fun exitVariableDeclarationListNotIn(ctx: VariableDeclarationListNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.variableDeclarationNotIn].
     * @param ctx the parse tree
     */
    fun enterVariableDeclarationNotIn(ctx: VariableDeclarationNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.variableDeclarationNotIn].
     * @param ctx the parse tree
     */
    fun exitVariableDeclarationNotIn(ctx: VariableDeclarationNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.initialiserNotIn].
     * @param ctx the parse tree
     */
    fun enterInitialiserNotIn(ctx: InitialiserNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.initialiserNotIn].
     * @param ctx the parse tree
     */
    fun exitInitialiserNotIn(ctx: InitialiserNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.expressionNotIn].
     * @param ctx the parse tree
     */
    fun enterExpressionNotIn(ctx: ExpressionNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.expressionNotIn].
     * @param ctx the parse tree
     */
    fun exitExpressionNotIn(ctx: ExpressionNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.assignmentExpressionNotIn].
     * @param ctx the parse tree
     */
    fun enterAssignmentExpressionNotIn(ctx: AssignmentExpressionNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.assignmentExpressionNotIn].
     * @param ctx the parse tree
     */
    fun exitAssignmentExpressionNotIn(ctx: AssignmentExpressionNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.assignmentOperator].
     * @param ctx the parse tree
     */
    fun enterAssignmentOperator(ctx: AssignmentOperatorContext?)

    /**
     * Exit a parse tree produced by [QMLParser.assignmentOperator].
     * @param ctx the parse tree
     */
    fun exitAssignmentOperator(ctx: AssignmentOperatorContext?)

    /**
     * Enter a parse tree produced by [QMLParser.conditionalExpressionNotIn].
     * @param ctx the parse tree
     */
    fun enterConditionalExpressionNotIn(ctx: ConditionalExpressionNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.conditionalExpressionNotIn].
     * @param ctx the parse tree
     */
    fun exitConditionalExpressionNotIn(ctx: ConditionalExpressionNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.logicalORExpressionNotIn].
     * @param ctx the parse tree
     */
    fun enterLogicalORExpressionNotIn(ctx: LogicalORExpressionNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.logicalORExpressionNotIn].
     * @param ctx the parse tree
     */
    fun exitLogicalORExpressionNotIn(ctx: LogicalORExpressionNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.logicalANDExpressionNotIn].
     * @param ctx the parse tree
     */
    fun enterLogicalANDExpressionNotIn(ctx: LogicalANDExpressionNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.logicalANDExpressionNotIn].
     * @param ctx the parse tree
     */
    fun exitLogicalANDExpressionNotIn(ctx: LogicalANDExpressionNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.bitwiseORExpressionNotIn].
     * @param ctx the parse tree
     */
    fun enterBitwiseORExpressionNotIn(ctx: BitwiseORExpressionNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.bitwiseORExpressionNotIn].
     * @param ctx the parse tree
     */
    fun exitBitwiseORExpressionNotIn(ctx: BitwiseORExpressionNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.bitwiseXORExpressionNotIn].
     * @param ctx the parse tree
     */
    fun enterBitwiseXORExpressionNotIn(ctx: BitwiseXORExpressionNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.bitwiseXORExpressionNotIn].
     * @param ctx the parse tree
     */
    fun exitBitwiseXORExpressionNotIn(ctx: BitwiseXORExpressionNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.bitwiseANDExpressionNotIn].
     * @param ctx the parse tree
     */
    fun enterBitwiseANDExpressionNotIn(ctx: BitwiseANDExpressionNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.bitwiseANDExpressionNotIn].
     * @param ctx the parse tree
     */
    fun exitBitwiseANDExpressionNotIn(ctx: BitwiseANDExpressionNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.equalityExpressionNotIn].
     * @param ctx the parse tree
     */
    fun enterEqualityExpressionNotIn(ctx: EqualityExpressionNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.equalityExpressionNotIn].
     * @param ctx the parse tree
     */
    fun exitEqualityExpressionNotIn(ctx: EqualityExpressionNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.relationalExpressionNotIn].
     * @param ctx the parse tree
     */
    fun enterRelationalExpressionNotIn(ctx: RelationalExpressionNotInContext?)

    /**
     * Exit a parse tree produced by [QMLParser.relationalExpressionNotIn].
     * @param ctx the parse tree
     */
    fun exitRelationalExpressionNotIn(ctx: RelationalExpressionNotInContext?)

    /**
     * Enter a parse tree produced by [QMLParser.labelledStatement].
     * @param ctx the parse tree
     */
    fun enterLabelledStatement(ctx: LabelledStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.labelledStatement].
     * @param ctx the parse tree
     */
    fun exitLabelledStatement(ctx: LabelledStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.breakStatement].
     * @param ctx the parse tree
     */
    fun enterBreakStatement(ctx: BreakStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.breakStatement].
     * @param ctx the parse tree
     */
    fun exitBreakStatement(ctx: BreakStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.returnStatement].
     * @param ctx the parse tree
     */
    fun enterReturnStatement(ctx: ReturnStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.returnStatement].
     * @param ctx the parse tree
     */
    fun exitReturnStatement(ctx: ReturnStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.tryStatement].
     * @param ctx the parse tree
     */
    fun enterTryStatement(ctx: TryStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.tryStatement].
     * @param ctx the parse tree
     */
    fun exitTryStatement(ctx: TryStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.catch_].
     * @param ctx the parse tree
     */
    fun enterCatch_(ctx: Catch_Context?)

    /**
     * Exit a parse tree produced by [QMLParser.catch_].
     * @param ctx the parse tree
     */
    fun exitCatch_(ctx: Catch_Context?)

    /**
     * Enter a parse tree produced by [QMLParser.finally_].
     * @param ctx the parse tree
     */
    fun enterFinally_(ctx: Finally_Context?)

    /**
     * Exit a parse tree produced by [QMLParser.finally_].
     * @param ctx the parse tree
     */
    fun exitFinally_(ctx: Finally_Context?)

    /**
     * Enter a parse tree produced by [QMLParser.block].
     * @param ctx the parse tree
     */
    fun enterBlock(ctx: BlockContext?)

    /**
     * Exit a parse tree produced by [QMLParser.block].
     * @param ctx the parse tree
     */
    fun exitBlock(ctx: BlockContext?)

    /**
     * Enter a parse tree produced by [QMLParser.caseBlock].
     * @param ctx the parse tree
     */
    fun enterCaseBlock(ctx: CaseBlockContext?)

    /**
     * Exit a parse tree produced by [QMLParser.caseBlock].
     * @param ctx the parse tree
     */
    fun exitCaseBlock(ctx: CaseBlockContext?)

    /**
     * Enter a parse tree produced by [QMLParser.caseClause].
     * @param ctx the parse tree
     */
    fun enterCaseClause(ctx: CaseClauseContext?)

    /**
     * Exit a parse tree produced by [QMLParser.caseClause].
     * @param ctx the parse tree
     */
    fun exitCaseClause(ctx: CaseClauseContext?)

    /**
     * Enter a parse tree produced by [QMLParser.defaultClause].
     * @param ctx the parse tree
     */
    fun enterDefaultClause(ctx: DefaultClauseContext?)

    /**
     * Exit a parse tree produced by [QMLParser.defaultClause].
     * @param ctx the parse tree
     */
    fun exitDefaultClause(ctx: DefaultClauseContext?)

    /**
     * Enter a parse tree produced by [QMLParser.switchStatement].
     * @param ctx the parse tree
     */
    fun enterSwitchStatement(ctx: SwitchStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.switchStatement].
     * @param ctx the parse tree
     */
    fun exitSwitchStatement(ctx: SwitchStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.withStatement].
     * @param ctx the parse tree
     */
    fun enterWithStatement(ctx: WithStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.withStatement].
     * @param ctx the parse tree
     */
    fun exitWithStatement(ctx: WithStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.ifStatement].
     * @param ctx the parse tree
     */
    fun enterIfStatement(ctx: IfStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.ifStatement].
     * @param ctx the parse tree
     */
    fun exitIfStatement(ctx: IfStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.statementListOpt].
     * @param ctx the parse tree
     */
    fun enterStatementListOpt(ctx: StatementListOptContext?)

    /**
     * Exit a parse tree produced by [QMLParser.statementListOpt].
     * @param ctx the parse tree
     */
    fun exitStatementListOpt(ctx: StatementListOptContext?)

    /**
     * Enter a parse tree produced by [QMLParser.statementList].
     * @param ctx the parse tree
     */
    fun enterStatementList(ctx: StatementListContext?)

    /**
     * Exit a parse tree produced by [QMLParser.statementList].
     * @param ctx the parse tree
     */
    fun exitStatementList(ctx: StatementListContext?)

    /**
     * Enter a parse tree produced by [QMLParser.emptyStatement].
     * @param ctx the parse tree
     */
    fun enterEmptyStatement(ctx: EmptyStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.emptyStatement].
     * @param ctx the parse tree
     */
    fun exitEmptyStatement(ctx: EmptyStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.expressionStatement].
     * @param ctx the parse tree
     */
    fun enterExpressionStatement(ctx: ExpressionStatementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.expressionStatement].
     * @param ctx the parse tree
     */
    fun exitExpressionStatement(ctx: ExpressionStatementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.expression].
     * @param ctx the parse tree
     */
    fun enterExpression(ctx: ExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.expression].
     * @param ctx the parse tree
     */
    fun exitExpression(ctx: ExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.assignmentExpression].
     * @param ctx the parse tree
     */
    fun enterAssignmentExpression(ctx: AssignmentExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.assignmentExpression].
     * @param ctx the parse tree
     */
    fun exitAssignmentExpression(ctx: AssignmentExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.leftHandSideExpression].
     * @param ctx the parse tree
     */
    fun enterLeftHandSideExpression(ctx: LeftHandSideExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.leftHandSideExpression].
     * @param ctx the parse tree
     */
    fun exitLeftHandSideExpression(ctx: LeftHandSideExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.callExpression].
     * @param ctx the parse tree
     */
    fun enterCallExpression(ctx: CallExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.callExpression].
     * @param ctx the parse tree
     */
    fun exitCallExpression(ctx: CallExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.newExpression].
     * @param ctx the parse tree
     */
    fun enterNewExpression(ctx: NewExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.newExpression].
     * @param ctx the parse tree
     */
    fun exitNewExpression(ctx: NewExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.memberExpression].
     * @param ctx the parse tree
     */
    fun enterMemberExpression(ctx: MemberExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.memberExpression].
     * @param ctx the parse tree
     */
    fun exitMemberExpression(ctx: MemberExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.argumentList].
     * @param ctx the parse tree
     */
    fun enterArgumentList(ctx: ArgumentListContext?)

    /**
     * Exit a parse tree produced by [QMLParser.argumentList].
     * @param ctx the parse tree
     */
    fun exitArgumentList(ctx: ArgumentListContext?)

    /**
     * Enter a parse tree produced by [QMLParser.propertyIdentifier].
     * @param ctx the parse tree
     */
    fun enterPropertyIdentifier(ctx: PropertyIdentifierContext?)

    /**
     * Exit a parse tree produced by [QMLParser.propertyIdentifier].
     * @param ctx the parse tree
     */
    fun exitPropertyIdentifier(ctx: PropertyIdentifierContext?)

    /**
     * Enter a parse tree produced by [QMLParser.functionExpression].
     * @param ctx the parse tree
     */
    fun enterFunctionExpression(ctx: FunctionExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.functionExpression].
     * @param ctx the parse tree
     */
    fun exitFunctionExpression(ctx: FunctionExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.formalParameterList].
     * @param ctx the parse tree
     */
    fun enterFormalParameterList(ctx: FormalParameterListContext?)

    /**
     * Exit a parse tree produced by [QMLParser.formalParameterList].
     * @param ctx the parse tree
     */
    fun exitFormalParameterList(ctx: FormalParameterListContext?)

    /**
     * Enter a parse tree produced by [QMLParser.functionBody].
     * @param ctx the parse tree
     */
    fun enterFunctionBody(ctx: FunctionBodyContext?)

    /**
     * Exit a parse tree produced by [QMLParser.functionBody].
     * @param ctx the parse tree
     */
    fun exitFunctionBody(ctx: FunctionBodyContext?)

    /**
     * Enter a parse tree produced by [QMLParser.sourceElement].
     * @param ctx the parse tree
     */
    fun enterSourceElement(ctx: SourceElementContext?)

    /**
     * Exit a parse tree produced by [QMLParser.sourceElement].
     * @param ctx the parse tree
     */
    fun exitSourceElement(ctx: SourceElementContext?)

    /**
     * Enter a parse tree produced by [QMLParser.functionDeclaration].
     * @param ctx the parse tree
     */
    fun enterFunctionDeclaration(ctx: FunctionDeclarationContext?)

    /**
     * Exit a parse tree produced by [QMLParser.functionDeclaration].
     * @param ctx the parse tree
     */
    fun exitFunctionDeclaration(ctx: FunctionDeclarationContext?)

    /**
     * Enter a parse tree produced by [QMLParser.primaryExpression].
     * @param ctx the parse tree
     */
    fun enterPrimaryExpression(ctx: PrimaryExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.primaryExpression].
     * @param ctx the parse tree
     */
    fun exitPrimaryExpression(ctx: PrimaryExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.propertyAssignmentListOpt].
     * @param ctx the parse tree
     */
    fun enterPropertyAssignmentListOpt(ctx: PropertyAssignmentListOptContext?)

    /**
     * Exit a parse tree produced by [QMLParser.propertyAssignmentListOpt].
     * @param ctx the parse tree
     */
    fun exitPropertyAssignmentListOpt(ctx: PropertyAssignmentListOptContext?)

    /**
     * Enter a parse tree produced by [QMLParser.popertyAssignmentList].
     * @param ctx the parse tree
     */
    fun enterPopertyAssignmentList(ctx: PopertyAssignmentListContext?)

    /**
     * Exit a parse tree produced by [QMLParser.popertyAssignmentList].
     * @param ctx the parse tree
     */
    fun exitPopertyAssignmentList(ctx: PopertyAssignmentListContext?)

    /**
     * Enter a parse tree produced by [QMLParser.propertyAssignmentList].
     * @param ctx the parse tree
     */
    fun enterPropertyAssignmentList(ctx: PropertyAssignmentListContext?)

    /**
     * Exit a parse tree produced by [QMLParser.propertyAssignmentList].
     * @param ctx the parse tree
     */
    fun exitPropertyAssignmentList(ctx: PropertyAssignmentListContext?)

    /**
     * Enter a parse tree produced by [QMLParser.propertyAssignment].
     * @param ctx the parse tree
     */
    fun enterPropertyAssignment(ctx: PropertyAssignmentContext?)

    /**
     * Exit a parse tree produced by [QMLParser.propertyAssignment].
     * @param ctx the parse tree
     */
    fun exitPropertyAssignment(ctx: PropertyAssignmentContext?)

    /**
     * Enter a parse tree produced by [QMLParser.propertyName].
     * @param ctx the parse tree
     */
    fun enterPropertyName(ctx: PropertyNameContext?)

    /**
     * Exit a parse tree produced by [QMLParser.propertyName].
     * @param ctx the parse tree
     */
    fun exitPropertyName(ctx: PropertyNameContext?)

    /**
     * Enter a parse tree produced by [QMLParser.elementList].
     * @param ctx the parse tree
     */
    fun enterElementList(ctx: ElementListContext?)

    /**
     * Exit a parse tree produced by [QMLParser.elementList].
     * @param ctx the parse tree
     */
    fun exitElementList(ctx: ElementListContext?)

    /**
     * Enter a parse tree produced by [QMLParser.elision].
     * @param ctx the parse tree
     */
    fun enterElision(ctx: ElisionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.elision].
     * @param ctx the parse tree
     */
    fun exitElision(ctx: ElisionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.conditionalExpression].
     * @param ctx the parse tree
     */
    fun enterConditionalExpression(ctx: ConditionalExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.conditionalExpression].
     * @param ctx the parse tree
     */
    fun exitConditionalExpression(ctx: ConditionalExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.logicalORExpression].
     * @param ctx the parse tree
     */
    fun enterLogicalORExpression(ctx: LogicalORExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.logicalORExpression].
     * @param ctx the parse tree
     */
    fun exitLogicalORExpression(ctx: LogicalORExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.logicalANDExpression].
     * @param ctx the parse tree
     */
    fun enterLogicalANDExpression(ctx: LogicalANDExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.logicalANDExpression].
     * @param ctx the parse tree
     */
    fun exitLogicalANDExpression(ctx: LogicalANDExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.bitwiseORExpression].
     * @param ctx the parse tree
     */
    fun enterBitwiseORExpression(ctx: BitwiseORExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.bitwiseORExpression].
     * @param ctx the parse tree
     */
    fun exitBitwiseORExpression(ctx: BitwiseORExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.bitwiseXORExpression].
     * @param ctx the parse tree
     */
    fun enterBitwiseXORExpression(ctx: BitwiseXORExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.bitwiseXORExpression].
     * @param ctx the parse tree
     */
    fun exitBitwiseXORExpression(ctx: BitwiseXORExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.bitwiseANDExpression].
     * @param ctx the parse tree
     */
    fun enterBitwiseANDExpression(ctx: BitwiseANDExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.bitwiseANDExpression].
     * @param ctx the parse tree
     */
    fun exitBitwiseANDExpression(ctx: BitwiseANDExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.equalityExpression].
     * @param ctx the parse tree
     */
    fun enterEqualityExpression(ctx: EqualityExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.equalityExpression].
     * @param ctx the parse tree
     */
    fun exitEqualityExpression(ctx: EqualityExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.relationalExpression].
     * @param ctx the parse tree
     */
    fun enterRelationalExpression(ctx: RelationalExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.relationalExpression].
     * @param ctx the parse tree
     */
    fun exitRelationalExpression(ctx: RelationalExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.shiftExpression].
     * @param ctx the parse tree
     */
    fun enterShiftExpression(ctx: ShiftExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.shiftExpression].
     * @param ctx the parse tree
     */
    fun exitShiftExpression(ctx: ShiftExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.additiveExpression].
     * @param ctx the parse tree
     */
    fun enterAdditiveExpression(ctx: AdditiveExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.additiveExpression].
     * @param ctx the parse tree
     */
    fun exitAdditiveExpression(ctx: AdditiveExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.multiplicativeExpression].
     * @param ctx the parse tree
     */
    fun enterMultiplicativeExpression(ctx: MultiplicativeExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.multiplicativeExpression].
     * @param ctx the parse tree
     */
    fun exitMultiplicativeExpression(ctx: MultiplicativeExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.unaryExpression].
     * @param ctx the parse tree
     */
    fun enterUnaryExpression(ctx: UnaryExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.unaryExpression].
     * @param ctx the parse tree
     */
    fun exitUnaryExpression(ctx: UnaryExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.postfixExpression].
     * @param ctx the parse tree
     */
    fun enterPostfixExpression(ctx: PostfixExpressionContext?)

    /**
     * Exit a parse tree produced by [QMLParser.postfixExpression].
     * @param ctx the parse tree
     */
    fun exitPostfixExpression(ctx: PostfixExpressionContext?)

    /**
     * Enter a parse tree produced by [QMLParser.reservedIdentifier].
     * @param ctx the parse tree
     */
    fun enterReservedIdentifier(ctx: ReservedIdentifierContext?)

    /**
     * Exit a parse tree produced by [QMLParser.reservedIdentifier].
     * @param ctx the parse tree
     */
    fun exitReservedIdentifier(ctx: ReservedIdentifierContext?)
}
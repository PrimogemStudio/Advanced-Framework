// Generated from /home/coder2/Advanced-Framework/uicompositor/src/main/resources/QML.g4 by ANTLR 4.13.1

package com.primogemstudio.advancedfmk.kui.qml.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QMLParser}.
 */
public interface QMLListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link QMLParser#program}.
     *
     * @param ctx the parse tree
     */
    void enterProgram(QMLParser.ProgramContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#program}.
     *
     * @param ctx the parse tree
     */
    void exitProgram(QMLParser.ProgramContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#import_}.
     *
     * @param ctx the parse tree
     */
    void enterImport_(QMLParser.Import_Context ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#import_}.
     *
     * @param ctx the parse tree
     */
    void exitImport_(QMLParser.Import_Context ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#importIdentifier}.
     *
     * @param ctx the parse tree
     */
    void enterImportIdentifier(QMLParser.ImportIdentifierContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#importIdentifier}.
     *
     * @param ctx the parse tree
     */
    void exitImportIdentifier(QMLParser.ImportIdentifierContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#importAlias}.
     *
     * @param ctx the parse tree
     */
    void enterImportAlias(QMLParser.ImportAliasContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#importAlias}.
     *
     * @param ctx the parse tree
     */
    void exitImportAlias(QMLParser.ImportAliasContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#rootMember}.
     *
     * @param ctx the parse tree
     */
    void enterRootMember(QMLParser.RootMemberContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#rootMember}.
     *
     * @param ctx the parse tree
     */
    void exitRootMember(QMLParser.RootMemberContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#objectDefinition}.
     *
     * @param ctx the parse tree
     */
    void enterObjectDefinition(QMLParser.ObjectDefinitionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#objectDefinition}.
     *
     * @param ctx the parse tree
     */
    void exitObjectDefinition(QMLParser.ObjectDefinitionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#qualifiedId}.
     *
     * @param ctx the parse tree
     */
    void enterQualifiedId(QMLParser.QualifiedIdContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#qualifiedId}.
     *
     * @param ctx the parse tree
     */
    void exitQualifiedId(QMLParser.QualifiedIdContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#objectInitializer}.
     *
     * @param ctx the parse tree
     */
    void enterObjectInitializer(QMLParser.ObjectInitializerContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#objectInitializer}.
     *
     * @param ctx the parse tree
     */
    void exitObjectInitializer(QMLParser.ObjectInitializerContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#objectMember}.
     *
     * @param ctx the parse tree
     */
    void enterObjectMember(QMLParser.ObjectMemberContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#objectMember}.
     *
     * @param ctx the parse tree
     */
    void exitObjectMember(QMLParser.ObjectMemberContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#propertyDeclaration}.
     *
     * @param ctx the parse tree
     */
    void enterPropertyDeclaration(QMLParser.PropertyDeclarationContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#propertyDeclaration}.
     *
     * @param ctx the parse tree
     */
    void exitPropertyDeclaration(QMLParser.PropertyDeclarationContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#propertyDeclarationAndAssignObjectDefinition}.
     *
     * @param ctx the parse tree
     */
    void enterPropertyDeclarationAndAssignObjectDefinition(QMLParser.PropertyDeclarationAndAssignObjectDefinitionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#propertyDeclarationAndAssignObjectDefinition}.
     *
     * @param ctx the parse tree
     */
    void exitPropertyDeclarationAndAssignObjectDefinition(QMLParser.PropertyDeclarationAndAssignObjectDefinitionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#parameterList}.
     *
     * @param ctx the parse tree
     */
    void enterParameterList(QMLParser.ParameterListContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#parameterList}.
     *
     * @param ctx the parse tree
     */
    void exitParameterList(QMLParser.ParameterListContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#propertyType}.
     *
     * @param ctx the parse tree
     */
    void enterPropertyType(QMLParser.PropertyTypeContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#propertyType}.
     *
     * @param ctx the parse tree
     */
    void exitPropertyType(QMLParser.PropertyTypeContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#arrayMemberList}.
     *
     * @param ctx the parse tree
     */
    void enterArrayMemberList(QMLParser.ArrayMemberListContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#arrayMemberList}.
     *
     * @param ctx the parse tree
     */
    void exitArrayMemberList(QMLParser.ArrayMemberListContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#scriptStatement}.
     *
     * @param ctx the parse tree
     */
    void enterScriptStatement(QMLParser.ScriptStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#scriptStatement}.
     *
     * @param ctx the parse tree
     */
    void exitScriptStatement(QMLParser.ScriptStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterStatement(QMLParser.StatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitStatement(QMLParser.StatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#variableStatement}.
     *
     * @param ctx the parse tree
     */
    void enterVariableStatement(QMLParser.VariableStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#variableStatement}.
     *
     * @param ctx the parse tree
     */
    void exitVariableStatement(QMLParser.VariableStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#variableDeclarationKind}.
     *
     * @param ctx the parse tree
     */
    void enterVariableDeclarationKind(QMLParser.VariableDeclarationKindContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#variableDeclarationKind}.
     *
     * @param ctx the parse tree
     */
    void exitVariableDeclarationKind(QMLParser.VariableDeclarationKindContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#variableDeclarationList}.
     *
     * @param ctx the parse tree
     */
    void enterVariableDeclarationList(QMLParser.VariableDeclarationListContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#variableDeclarationList}.
     *
     * @param ctx the parse tree
     */
    void exitVariableDeclarationList(QMLParser.VariableDeclarationListContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#variableDeclaration}.
     *
     * @param ctx the parse tree
     */
    void enterVariableDeclaration(QMLParser.VariableDeclarationContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#variableDeclaration}.
     *
     * @param ctx the parse tree
     */
    void exitVariableDeclaration(QMLParser.VariableDeclarationContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#initialiser}.
     *
     * @param ctx the parse tree
     */
    void enterInitialiser(QMLParser.InitialiserContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#initialiser}.
     *
     * @param ctx the parse tree
     */
    void exitInitialiser(QMLParser.InitialiserContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#debuggerStatement}.
     *
     * @param ctx the parse tree
     */
    void enterDebuggerStatement(QMLParser.DebuggerStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#debuggerStatement}.
     *
     * @param ctx the parse tree
     */
    void exitDebuggerStatement(QMLParser.DebuggerStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#throwStatement}.
     *
     * @param ctx the parse tree
     */
    void enterThrowStatement(QMLParser.ThrowStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#throwStatement}.
     *
     * @param ctx the parse tree
     */
    void exitThrowStatement(QMLParser.ThrowStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#continueStatement}.
     *
     * @param ctx the parse tree
     */
    void enterContinueStatement(QMLParser.ContinueStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#continueStatement}.
     *
     * @param ctx the parse tree
     */
    void exitContinueStatement(QMLParser.ContinueStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#iterationStatement}.
     *
     * @param ctx the parse tree
     */
    void enterIterationStatement(QMLParser.IterationStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#iterationStatement}.
     *
     * @param ctx the parse tree
     */
    void exitIterationStatement(QMLParser.IterationStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#variableDeclarationListNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterVariableDeclarationListNotIn(QMLParser.VariableDeclarationListNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#variableDeclarationListNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitVariableDeclarationListNotIn(QMLParser.VariableDeclarationListNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#variableDeclarationNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterVariableDeclarationNotIn(QMLParser.VariableDeclarationNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#variableDeclarationNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitVariableDeclarationNotIn(QMLParser.VariableDeclarationNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#initialiserNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterInitialiserNotIn(QMLParser.InitialiserNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#initialiserNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitInitialiserNotIn(QMLParser.InitialiserNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#expressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterExpressionNotIn(QMLParser.ExpressionNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#expressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitExpressionNotIn(QMLParser.ExpressionNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#assignmentExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterAssignmentExpressionNotIn(QMLParser.AssignmentExpressionNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#assignmentExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitAssignmentExpressionNotIn(QMLParser.AssignmentExpressionNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#assignmentOperator}.
     *
     * @param ctx the parse tree
     */
    void enterAssignmentOperator(QMLParser.AssignmentOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#assignmentOperator}.
     *
     * @param ctx the parse tree
     */
    void exitAssignmentOperator(QMLParser.AssignmentOperatorContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#conditionalExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterConditionalExpressionNotIn(QMLParser.ConditionalExpressionNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#conditionalExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitConditionalExpressionNotIn(QMLParser.ConditionalExpressionNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#logicalORExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterLogicalORExpressionNotIn(QMLParser.LogicalORExpressionNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#logicalORExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitLogicalORExpressionNotIn(QMLParser.LogicalORExpressionNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#logicalANDExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterLogicalANDExpressionNotIn(QMLParser.LogicalANDExpressionNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#logicalANDExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitLogicalANDExpressionNotIn(QMLParser.LogicalANDExpressionNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#bitwiseORExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterBitwiseORExpressionNotIn(QMLParser.BitwiseORExpressionNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#bitwiseORExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitBitwiseORExpressionNotIn(QMLParser.BitwiseORExpressionNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#bitwiseXORExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterBitwiseXORExpressionNotIn(QMLParser.BitwiseXORExpressionNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#bitwiseXORExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitBitwiseXORExpressionNotIn(QMLParser.BitwiseXORExpressionNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#bitwiseANDExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterBitwiseANDExpressionNotIn(QMLParser.BitwiseANDExpressionNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#bitwiseANDExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitBitwiseANDExpressionNotIn(QMLParser.BitwiseANDExpressionNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#equalityExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterEqualityExpressionNotIn(QMLParser.EqualityExpressionNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#equalityExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitEqualityExpressionNotIn(QMLParser.EqualityExpressionNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#relationalExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void enterRelationalExpressionNotIn(QMLParser.RelationalExpressionNotInContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#relationalExpressionNotIn}.
     *
     * @param ctx the parse tree
     */
    void exitRelationalExpressionNotIn(QMLParser.RelationalExpressionNotInContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#labelledStatement}.
     *
     * @param ctx the parse tree
     */
    void enterLabelledStatement(QMLParser.LabelledStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#labelledStatement}.
     *
     * @param ctx the parse tree
     */
    void exitLabelledStatement(QMLParser.LabelledStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#breakStatement}.
     *
     * @param ctx the parse tree
     */
    void enterBreakStatement(QMLParser.BreakStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#breakStatement}.
     *
     * @param ctx the parse tree
     */
    void exitBreakStatement(QMLParser.BreakStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#returnStatement}.
     *
     * @param ctx the parse tree
     */
    void enterReturnStatement(QMLParser.ReturnStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#returnStatement}.
     *
     * @param ctx the parse tree
     */
    void exitReturnStatement(QMLParser.ReturnStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#tryStatement}.
     *
     * @param ctx the parse tree
     */
    void enterTryStatement(QMLParser.TryStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#tryStatement}.
     *
     * @param ctx the parse tree
     */
    void exitTryStatement(QMLParser.TryStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#catch_}.
     *
     * @param ctx the parse tree
     */
    void enterCatch_(QMLParser.Catch_Context ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#catch_}.
     *
     * @param ctx the parse tree
     */
    void exitCatch_(QMLParser.Catch_Context ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#finally_}.
     *
     * @param ctx the parse tree
     */
    void enterFinally_(QMLParser.Finally_Context ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#finally_}.
     *
     * @param ctx the parse tree
     */
    void exitFinally_(QMLParser.Finally_Context ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#block}.
     *
     * @param ctx the parse tree
     */
    void enterBlock(QMLParser.BlockContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#block}.
     *
     * @param ctx the parse tree
     */
    void exitBlock(QMLParser.BlockContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#caseBlock}.
     *
     * @param ctx the parse tree
     */
    void enterCaseBlock(QMLParser.CaseBlockContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#caseBlock}.
     *
     * @param ctx the parse tree
     */
    void exitCaseBlock(QMLParser.CaseBlockContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#caseClause}.
     *
     * @param ctx the parse tree
     */
    void enterCaseClause(QMLParser.CaseClauseContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#caseClause}.
     *
     * @param ctx the parse tree
     */
    void exitCaseClause(QMLParser.CaseClauseContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#defaultClause}.
     *
     * @param ctx the parse tree
     */
    void enterDefaultClause(QMLParser.DefaultClauseContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#defaultClause}.
     *
     * @param ctx the parse tree
     */
    void exitDefaultClause(QMLParser.DefaultClauseContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#switchStatement}.
     *
     * @param ctx the parse tree
     */
    void enterSwitchStatement(QMLParser.SwitchStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#switchStatement}.
     *
     * @param ctx the parse tree
     */
    void exitSwitchStatement(QMLParser.SwitchStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#withStatement}.
     *
     * @param ctx the parse tree
     */
    void enterWithStatement(QMLParser.WithStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#withStatement}.
     *
     * @param ctx the parse tree
     */
    void exitWithStatement(QMLParser.WithStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#ifStatement}.
     *
     * @param ctx the parse tree
     */
    void enterIfStatement(QMLParser.IfStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#ifStatement}.
     *
     * @param ctx the parse tree
     */
    void exitIfStatement(QMLParser.IfStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#statementListOpt}.
     *
     * @param ctx the parse tree
     */
    void enterStatementListOpt(QMLParser.StatementListOptContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#statementListOpt}.
     *
     * @param ctx the parse tree
     */
    void exitStatementListOpt(QMLParser.StatementListOptContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#statementList}.
     *
     * @param ctx the parse tree
     */
    void enterStatementList(QMLParser.StatementListContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#statementList}.
     *
     * @param ctx the parse tree
     */
    void exitStatementList(QMLParser.StatementListContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#emptyStatement}.
     *
     * @param ctx the parse tree
     */
    void enterEmptyStatement(QMLParser.EmptyStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#emptyStatement}.
     *
     * @param ctx the parse tree
     */
    void exitEmptyStatement(QMLParser.EmptyStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#expressionStatement}.
     *
     * @param ctx the parse tree
     */
    void enterExpressionStatement(QMLParser.ExpressionStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#expressionStatement}.
     *
     * @param ctx the parse tree
     */
    void exitExpressionStatement(QMLParser.ExpressionStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterExpression(QMLParser.ExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitExpression(QMLParser.ExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#assignmentExpression}.
     *
     * @param ctx the parse tree
     */
    void enterAssignmentExpression(QMLParser.AssignmentExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#assignmentExpression}.
     *
     * @param ctx the parse tree
     */
    void exitAssignmentExpression(QMLParser.AssignmentExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#leftHandSideExpression}.
     *
     * @param ctx the parse tree
     */
    void enterLeftHandSideExpression(QMLParser.LeftHandSideExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#leftHandSideExpression}.
     *
     * @param ctx the parse tree
     */
    void exitLeftHandSideExpression(QMLParser.LeftHandSideExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#callExpression}.
     *
     * @param ctx the parse tree
     */
    void enterCallExpression(QMLParser.CallExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#callExpression}.
     *
     * @param ctx the parse tree
     */
    void exitCallExpression(QMLParser.CallExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#newExpression}.
     *
     * @param ctx the parse tree
     */
    void enterNewExpression(QMLParser.NewExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#newExpression}.
     *
     * @param ctx the parse tree
     */
    void exitNewExpression(QMLParser.NewExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#memberExpression}.
     *
     * @param ctx the parse tree
     */
    void enterMemberExpression(QMLParser.MemberExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#memberExpression}.
     *
     * @param ctx the parse tree
     */
    void exitMemberExpression(QMLParser.MemberExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#argumentList}.
     *
     * @param ctx the parse tree
     */
    void enterArgumentList(QMLParser.ArgumentListContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#argumentList}.
     *
     * @param ctx the parse tree
     */
    void exitArgumentList(QMLParser.ArgumentListContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#propertyIdentifier}.
     *
     * @param ctx the parse tree
     */
    void enterPropertyIdentifier(QMLParser.PropertyIdentifierContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#propertyIdentifier}.
     *
     * @param ctx the parse tree
     */
    void exitPropertyIdentifier(QMLParser.PropertyIdentifierContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#functionExpression}.
     *
     * @param ctx the parse tree
     */
    void enterFunctionExpression(QMLParser.FunctionExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#functionExpression}.
     *
     * @param ctx the parse tree
     */
    void exitFunctionExpression(QMLParser.FunctionExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#formalParameterList}.
     *
     * @param ctx the parse tree
     */
    void enterFormalParameterList(QMLParser.FormalParameterListContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#formalParameterList}.
     *
     * @param ctx the parse tree
     */
    void exitFormalParameterList(QMLParser.FormalParameterListContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#functionBody}.
     *
     * @param ctx the parse tree
     */
    void enterFunctionBody(QMLParser.FunctionBodyContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#functionBody}.
     *
     * @param ctx the parse tree
     */
    void exitFunctionBody(QMLParser.FunctionBodyContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#sourceElement}.
     *
     * @param ctx the parse tree
     */
    void enterSourceElement(QMLParser.SourceElementContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#sourceElement}.
     *
     * @param ctx the parse tree
     */
    void exitSourceElement(QMLParser.SourceElementContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#functionDeclaration}.
     *
     * @param ctx the parse tree
     */
    void enterFunctionDeclaration(QMLParser.FunctionDeclarationContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#functionDeclaration}.
     *
     * @param ctx the parse tree
     */
    void exitFunctionDeclaration(QMLParser.FunctionDeclarationContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#primaryExpression}.
     *
     * @param ctx the parse tree
     */
    void enterPrimaryExpression(QMLParser.PrimaryExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#primaryExpression}.
     *
     * @param ctx the parse tree
     */
    void exitPrimaryExpression(QMLParser.PrimaryExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#propertyAssignmentListOpt}.
     *
     * @param ctx the parse tree
     */
    void enterPropertyAssignmentListOpt(QMLParser.PropertyAssignmentListOptContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#propertyAssignmentListOpt}.
     *
     * @param ctx the parse tree
     */
    void exitPropertyAssignmentListOpt(QMLParser.PropertyAssignmentListOptContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#popertyAssignmentList}.
     *
     * @param ctx the parse tree
     */
    void enterPopertyAssignmentList(QMLParser.PopertyAssignmentListContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#popertyAssignmentList}.
     *
     * @param ctx the parse tree
     */
    void exitPopertyAssignmentList(QMLParser.PopertyAssignmentListContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#propertyAssignmentList}.
     *
     * @param ctx the parse tree
     */
    void enterPropertyAssignmentList(QMLParser.PropertyAssignmentListContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#propertyAssignmentList}.
     *
     * @param ctx the parse tree
     */
    void exitPropertyAssignmentList(QMLParser.PropertyAssignmentListContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#propertyAssignment}.
     *
     * @param ctx the parse tree
     */
    void enterPropertyAssignment(QMLParser.PropertyAssignmentContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#propertyAssignment}.
     *
     * @param ctx the parse tree
     */
    void exitPropertyAssignment(QMLParser.PropertyAssignmentContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#propertyName}.
     *
     * @param ctx the parse tree
     */
    void enterPropertyName(QMLParser.PropertyNameContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#propertyName}.
     *
     * @param ctx the parse tree
     */
    void exitPropertyName(QMLParser.PropertyNameContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#elementList}.
     *
     * @param ctx the parse tree
     */
    void enterElementList(QMLParser.ElementListContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#elementList}.
     *
     * @param ctx the parse tree
     */
    void exitElementList(QMLParser.ElementListContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#elision}.
     *
     * @param ctx the parse tree
     */
    void enterElision(QMLParser.ElisionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#elision}.
     *
     * @param ctx the parse tree
     */
    void exitElision(QMLParser.ElisionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#conditionalExpression}.
     *
     * @param ctx the parse tree
     */
    void enterConditionalExpression(QMLParser.ConditionalExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#conditionalExpression}.
     *
     * @param ctx the parse tree
     */
    void exitConditionalExpression(QMLParser.ConditionalExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#logicalORExpression}.
     *
     * @param ctx the parse tree
     */
    void enterLogicalORExpression(QMLParser.LogicalORExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#logicalORExpression}.
     *
     * @param ctx the parse tree
     */
    void exitLogicalORExpression(QMLParser.LogicalORExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#logicalANDExpression}.
     *
     * @param ctx the parse tree
     */
    void enterLogicalANDExpression(QMLParser.LogicalANDExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#logicalANDExpression}.
     *
     * @param ctx the parse tree
     */
    void exitLogicalANDExpression(QMLParser.LogicalANDExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#bitwiseORExpression}.
     *
     * @param ctx the parse tree
     */
    void enterBitwiseORExpression(QMLParser.BitwiseORExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#bitwiseORExpression}.
     *
     * @param ctx the parse tree
     */
    void exitBitwiseORExpression(QMLParser.BitwiseORExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#bitwiseXORExpression}.
     *
     * @param ctx the parse tree
     */
    void enterBitwiseXORExpression(QMLParser.BitwiseXORExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#bitwiseXORExpression}.
     *
     * @param ctx the parse tree
     */
    void exitBitwiseXORExpression(QMLParser.BitwiseXORExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#bitwiseANDExpression}.
     *
     * @param ctx the parse tree
     */
    void enterBitwiseANDExpression(QMLParser.BitwiseANDExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#bitwiseANDExpression}.
     *
     * @param ctx the parse tree
     */
    void exitBitwiseANDExpression(QMLParser.BitwiseANDExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#equalityExpression}.
     *
     * @param ctx the parse tree
     */
    void enterEqualityExpression(QMLParser.EqualityExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#equalityExpression}.
     *
     * @param ctx the parse tree
     */
    void exitEqualityExpression(QMLParser.EqualityExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#relationalExpression}.
     *
     * @param ctx the parse tree
     */
    void enterRelationalExpression(QMLParser.RelationalExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#relationalExpression}.
     *
     * @param ctx the parse tree
     */
    void exitRelationalExpression(QMLParser.RelationalExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#shiftExpression}.
     *
     * @param ctx the parse tree
     */
    void enterShiftExpression(QMLParser.ShiftExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#shiftExpression}.
     *
     * @param ctx the parse tree
     */
    void exitShiftExpression(QMLParser.ShiftExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#additiveExpression}.
     *
     * @param ctx the parse tree
     */
    void enterAdditiveExpression(QMLParser.AdditiveExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#additiveExpression}.
     *
     * @param ctx the parse tree
     */
    void exitAdditiveExpression(QMLParser.AdditiveExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#multiplicativeExpression}.
     *
     * @param ctx the parse tree
     */
    void enterMultiplicativeExpression(QMLParser.MultiplicativeExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#multiplicativeExpression}.
     *
     * @param ctx the parse tree
     */
    void exitMultiplicativeExpression(QMLParser.MultiplicativeExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#unaryExpression}.
     *
     * @param ctx the parse tree
     */
    void enterUnaryExpression(QMLParser.UnaryExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#unaryExpression}.
     *
     * @param ctx the parse tree
     */
    void exitUnaryExpression(QMLParser.UnaryExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#postfixExpression}.
     *
     * @param ctx the parse tree
     */
    void enterPostfixExpression(QMLParser.PostfixExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#postfixExpression}.
     *
     * @param ctx the parse tree
     */
    void exitPostfixExpression(QMLParser.PostfixExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link QMLParser#reservedIdentifier}.
     *
     * @param ctx the parse tree
     */
    void enterReservedIdentifier(QMLParser.ReservedIdentifierContext ctx);

    /**
     * Exit a parse tree produced by {@link QMLParser#reservedIdentifier}.
     *
     * @param ctx the parse tree
     */
    void exitReservedIdentifier(QMLParser.ReservedIdentifierContext ctx);
}
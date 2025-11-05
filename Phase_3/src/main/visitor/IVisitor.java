package main.visitor;

import main.ast.nodes.*;
import main.ast.nodes.expr.*;
import main.ast.nodes.Stmt.*;
import main.symbolTable.exceptions.ItemNotFoundException;


public interface IVisitor<T> {

    public T visit(Program program);
    public T visit(ArgumentExpressionList argumentExpressionList) throws ItemNotFoundException;
    public T visit(AssignExpr assignExpr);
    public T visit(BinaryExpr binaryExpr);
    public T visit(BracketExpr bracketExpr);
    public T visit(CastExpression castExpression);
    public T visit(CompoundLiteralExpr compoundLiteralExpr);
    public T visit(ConstantExpr constantExpr);
    public T visit(ExprList exprList);
    public T visit(FuncCallExpr funcCallExpr);
    public T visit(Identifier identifier);
    public T visit(IdExpr idExpr);
    public T visit(IfExpr ifExpr);
    public T visit(StringLiteralExpr stringLiteralExpr);
    public T visit(TypeName typeName);
    public T visit(UnaryExpr unaryExpr);
    public T visit(UnaryOperator unaryOperator);
    public T visit(BlockItem blockItem);
    public T visit(BreakState breakState);
    public T visit(CompoundStatement compoundStatement);
    public T visit(ContinueState continueState);
    public T visit(ExpressionStatement expressionStatement);
    public T visit(IterationStatement iterationStatement);
    public T visit(JumpStatement jumpStatement);
    public T visit(ReturnState returnState);
    public T visit(SelectionStatement selectionStatement);
    public T visit(AbstractDeclarator abstractDeclarator);
    public T visit(AssignmentOperator assignmentOperator);
    public T visit(CustomType customType);
    public T visit(Declaration Declaration);
    public T visit(DeclarationList declarationList);
    public T visit(DeclarationSpecifier declarationSpecifier);
    public T visit(DeclarationSpecifiers declarationSpecifiers);
    public T visit(Declarator declarator);
    public T visit(Designation designation);
    public T visit(Designator designator);
    public T visit(DirectAbstractDeclarator directAbstractDeclarator);
    public T visit(DirectDeclarator directDeclarator);
    public T visit(ExternalDeclaration externalDeclaration);
    public T visit(ForCondition forCondition);
    public T visit(ForDeclaration forDeclaration);
    public T visit(ForExpression forExpression);
    public T visit(FunctionDefinition functionDefinition);
    public T visit(IdentifierList identifierList);
    public T visit(InitDeclarator initDeclarator);
    public T visit(InitDeclaratorList initDeclaratorList);
    public T visit(Initializer initializer);
    public T visit(InitializerList initializerList);
    public T visit(ParameterDeclaration parameterDeclaration);
    public T visit(ParameterList parameterList);
    public T visit(Pointer pointer);
    public T visit(PremetiveType premetiveType);
    public T visit(SpecifierQualifierList specifierQualifierList);
    public T visit(Symbol symbol);
    public T visit(TranslationUnit translationUnit);
    public T visit(Type type);

}

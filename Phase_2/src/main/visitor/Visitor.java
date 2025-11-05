package main.visitor;

import main.ast.nodes.*;
import main.ast.nodes.expr.*;
import main.ast.nodes.Stmt.*;

/*GOALs:
*   1. print out scope changes each time a new scope starts
*   2. print the identifier if it is initialized
*   3. print the identifier if it is used
*   4. print out the name of the function when it is defined
*   5. print out the name of the function when it is used
*
* */

public abstract class Visitor<T> implements IVisitor<T> {
    @Override
    public T visit(Program program) {
        return null;
    }
    public T visit(ArgumentExpressionList argumentExpressionList) {
        return null;
    }
    public T visit(AssignExpr assignExpr) {
        return null;
    }
    public T visit(BinaryExpr binaryExpr) {
        return null;
    }
    public T visit(BracketExpr bracketExpr) {
        return null;
    }
    public T visit(CastExpression castExpression) {
        return null;
    }
    public T visit(CompoundLiteralExpr compoundLiteralExpr) {
        return null;
    }
    public T visit(ConstantExpr constantExpr) {
        return null;
    }
    public T visit(ExprList exprList) {
        return null;
    }
    public T visit(FuncCallExpr funcCallExpr) {
        return null;
    }
    public T visit(Identifier identifier) {
        return null;
    }
    public T visit(IdExpr idExpr) {
        return null;
    }
    public T visit(IfExpr ifExpr) {
        return null;
    }
    public T visit(StringLiteralExpr stringLiteralExpr) {
        return null;
    }
    public T visit(TypeName typeName) {
        return null;
    }
    public T visit(UnaryExpr unaryExpr) {
        return null;
    }
    public T visit(UnaryOperator unaryOperator) {
        return null;
    }
    public T visit(BlockItem blockItem) {
        return null;
    }
    public T visit(BreakState breakState) {
        return null;
    }
    public T visit(CompoundStatement compoundStatement) {
        return null;
    }
    public T visit(ContinueState continueState) {
        return null;
    }
    public T visit(ExpressionStatement expressionStatement) {
        return null;
    }
    public T visit(IterationStatement iterationStatement) {
        return null;
    }
    public T visit(JumpStatement jumpStatement) {
        return null;
    }
    public T visit(ReturnState returnState) {
        return null;
    }
    public T visit(SelectionStatement selectionStatement) {
        return null;
    }
    public T visit(AbstractDeclarator abstractDeclarator) {
        return null;
    }
    public T visit(AssignmentOperator assignmentOperator) {
        return null;
    }
    public T visit(CustomType customType) {
        return null;
    }
    public T visit(Declaration Declaration) {
        return null;
    }
    public T visit(DeclarationList declarationList) {
        return null;
    }
    public T visit(DeclarationSpecifier declarationSpecifier) {
        return null;
    }
    public T visit(DeclarationSpecifiers declarationSpecifiers) {
        return null;
    }
    public T visit(Declarator declarator) {
        return null;
    }
    public T visit(Designation designation) {
        return null;
    }
    public T visit(Designator designator) {
        return null;
    }
    public T visit(DirectAbstractDeclarator directAbstractDeclarator) {
        return null;
    }
    public T visit(DirectDeclarator directDeclarator) {
        return null;
    }
    public T visit(ExternalDeclaration externalDeclaration) {
        return null;
    }
    public T visit(ForCondition forCondition) {
        return null;
    }
    public T visit(ForDeclaration forDeclaration) {
        return null;
    }
    public T visit(ForExpression forExpression) {
        return null;
    }
    public T visit(FunctionDefinition functionDefinition) {
        return null;
    }
    public T visit(IdentifierList identifierList) {
        return null;
    }
    public T visit(InitDeclarator initDeclarator) {
        return null;
    }
    public T visit(InitDeclaratorList initDeclaratorList) {
        return null;
    }
    public T visit(Initializer initializer) {
        return null;
    }
    public T visit(ParameterDeclaration parameterDeclaration) {
        return null;
    }
    public T visit(ParameterList parameterList) {
        return null;
    }
    public T visit(Pointer pointer) {
        return null;
    }
    public T visit(PremetiveType premetiveType) {
        return null;
    }
    public T visit(SpecifierQualifierList specifierQualifierList) {
        return null;
    }
    public T visit(Symbol symbol) {
        return null;
    }
    public T visit(TranslationUnit translationUnit) {
        return null;
    }
    public T visit(Type type) {
        return null;
    }
    public T visit(InitializerList initializerList) {
        return null;
    }



}

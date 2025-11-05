package main.ast.nodes;

import main.ast.nodes.Stmt.CompoundStatement;
import main.visitor.IVisitor;

public class FunctionDefinition extends Node {
    private DeclarationList declarationList;
    private Declarator declarator;
    private DeclarationSpecifiers declarationSpecifiers;
    private CompoundStatement compoundStatement;

    public FunctionDefinition(){ }

    public CompoundStatement getCompoundStatement() {
        return compoundStatement;
    }

    public void setCompoundStatement(CompoundStatement compoundStatement) {
        this.compoundStatement = compoundStatement;
    }

    public Declarator getDeclarator() {
        return declarator;
    }

    public void setDeclarator(Declarator declarator) {
        this.declarator = declarator;
    }

    public DeclarationSpecifiers getDeclarationSpecifiers() {
        return declarationSpecifiers;
    }

    public void setDeclarationSpecifiers(DeclarationSpecifiers declarationSpecifiers) {
        this.declarationSpecifiers = declarationSpecifiers;
    }

    public DeclarationList getDeclarationList() {
        return declarationList;
    }

    public void setDeclarationList(DeclarationList declarationList) {
        this.declarationList = declarationList;
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

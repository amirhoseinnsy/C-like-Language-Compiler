package main.ast.nodes;

import main.visitor.IVisitor;

public class ExternalDeclaration extends Node{
    private FunctionDefinition FD;
    private Declaration declaration;
    public ExternalDeclaration() { }

    public FunctionDefinition getFD() {
        return FD;
    }

    public void setFD(FunctionDefinition FD) {
        this.FD = FD;
    }

    public Declaration getDeclaration() {
        return declaration;
    }

    public void setDeclaration(Declaration declaration) {
        this.declaration = declaration;
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

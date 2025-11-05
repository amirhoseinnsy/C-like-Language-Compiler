package main.ast.nodes;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class DeclarationList extends Node{
    private ArrayList<Declaration> declarations = new ArrayList<>();

    public DeclarationList() {}

    public void sddDeclaration(Declaration declaration) {
        this.declarations.add(declaration);
    }

    public ArrayList<Declaration> getDeclarations() {
        return declarations;
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

package main.ast.nodes;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class DeclarationSpecifiers extends Node {
    public ArrayList<DeclarationSpecifier> ds = new ArrayList<>();
    public DeclarationSpecifiers() {}
    public void addDs(DeclarationSpecifier ds) {
        this.ds.add(ds);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ArrayList<DeclarationSpecifier> getDs() {
        return ds;
    }
}

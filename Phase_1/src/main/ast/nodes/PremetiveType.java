package main.ast.nodes;

import main.visitor.IVisitor;

public class PremetiveType extends Type {
    public String name;
    public PremetiveType(String name) {
        this.name = name;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

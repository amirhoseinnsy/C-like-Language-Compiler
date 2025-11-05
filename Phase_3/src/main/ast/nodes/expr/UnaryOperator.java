package main.ast.nodes.expr;

import main.ast.nodes.Node;
import main.visitor.IVisitor;

public class UnaryOperator extends Node {
    private String name;

    public UnaryOperator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

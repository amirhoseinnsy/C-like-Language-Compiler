package main.ast.nodes.Stmt;

import main.ast.nodes.Node;
import main.visitor.IVisitor;

public abstract class BreakState extends Node {
    public BreakState() {
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

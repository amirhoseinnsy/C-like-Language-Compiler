package main.ast.nodes.Stmt;

import main.ast.nodes.Node;
import main.visitor.IVisitor;

public abstract class ContinueState extends Node {
    public ContinueState() {
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

package main.ast.nodes.Stmt;

import main.ast.nodes.Node;
import main.ast.nodes.expr.Expr;
import main.visitor.IVisitor;

public abstract class ReturnState extends Node {
    public Expr Value;

    public Expr getE() {
        return Value;
    }

    public void setE(Expr value) {
        Value = value;
    }

    public ReturnState(Expr Value) {
        this.Value = Value;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
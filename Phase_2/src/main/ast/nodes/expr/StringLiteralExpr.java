package main.ast.nodes.expr;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class StringLiteralExpr extends Expr {
    public String parts;

    public StringLiteralExpr(String parts) {
        this.parts = parts;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
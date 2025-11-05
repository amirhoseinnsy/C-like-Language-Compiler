package main.ast.nodes.expr;

import main.visitor.IVisitor;

public class BracketExpr extends Expr {
    private Expr e1;
    private Expr e2;

    public BracketExpr(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expr getE1() {
        return e1;
    }

    public Expr getE2() {
        return e2;
    }
}

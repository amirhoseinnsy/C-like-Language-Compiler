package main.ast.nodes.expr;

import main.visitor.IVisitor;

public class IfExpr extends Expr{
    private Expr e1;
    private Expr e2;
    private Expr e3;

    public IfExpr(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
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

    public Expr getE3() {
        return e3;
    }
}

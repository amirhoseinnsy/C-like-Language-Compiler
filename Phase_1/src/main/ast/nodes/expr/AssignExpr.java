package main.ast.nodes.expr;

import main.ast.nodes.AssignmentOperator;
import main.visitor.IVisitor;

public class AssignExpr extends Expr{
    private Expr e1;
    private Expr e2;
    private AssignmentOperator ao;

    public AssignExpr(Expr e1, Expr e2, AssignmentOperator ao) {
        this.e1 = e1;
        this.e2 = e2;
        this.ao = ao;
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

    public AssignmentOperator getAo() {
        return ao;
    }
}

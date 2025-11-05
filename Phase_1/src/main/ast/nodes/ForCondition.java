package main.ast.nodes;

import main.ast.nodes.expr.Expr;
import main.visitor.IVisitor;

public class ForCondition extends Node {
    private ForDeclaration FD;
    private Expr e;
    private ForExpression FE1, FE2;

    public ForCondition() {
    }

    public ForDeclaration getFD() {
        return FD;
    }

    public void setFD(ForDeclaration FD) {
        this.FD = FD;
    }

    public Expr getE() {
        return e;
    }

    public void setE(Expr e) {
        this.e = e;
    }

    public ForExpression getFE1() {
        return FE1;
    }

    public void setFE1(ForExpression FE1) {
        this.FE1 = FE1;
    }

    public ForExpression getFE2() {
        return FE2;
    }

    public void setFE2(ForExpression FE2) {
        this.FE2 = FE2;
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

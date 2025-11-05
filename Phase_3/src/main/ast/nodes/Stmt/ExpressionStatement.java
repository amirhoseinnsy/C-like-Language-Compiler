package main.ast.nodes.Stmt;

import main.ast.nodes.expr.Expr;
import main.visitor.IVisitor;

public class ExpressionStatement extends Stmt{
    private Expr e;

    public ExpressionStatement() {
        this.setType(2);
    }

    public Expr getE() {
        return e;
    }

    public void setE(Expr e) {
        this.e = e;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

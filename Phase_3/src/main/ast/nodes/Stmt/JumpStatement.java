package main.ast.nodes.Stmt;

import main.ast.nodes.expr.Expr;
import main.visitor.IVisitor;

public class JumpStatement extends Stmt{
    private Expr e;
    private String State;

    public JumpStatement() {
        this.setType(5);
    }

    public Expr getE() {
        return e;
    }

    public void setE(Expr e) {
        this.e = e;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

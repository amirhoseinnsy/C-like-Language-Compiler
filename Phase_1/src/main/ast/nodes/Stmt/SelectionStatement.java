package main.ast.nodes.Stmt;

import main.ast.nodes.expr.Expr;
import main.visitor.IVisitor;

public class SelectionStatement extends Stmt{
    private Expr e;
    private Stmt s1, s2;
    private int stmtIndex;
    public void setStmtIndex(int idx) { this.stmtIndex = idx; }
    public int getStmtIndex() { return stmtIndex; }


    public SelectionStatement() {
        this.setType(3);
    }

    public Expr getE() {
        return e;
    }

    public void setE(Expr e) {
        this.e = e;
    }

    public Stmt getS1() {
        return s1;
    }

    public void setS1(Stmt s1) {
        this.s1 = s1;
    }

    public Stmt getS2() {
        return s2;
    }

    public void setS2(Stmt s2) {
        this.s2 = s2;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

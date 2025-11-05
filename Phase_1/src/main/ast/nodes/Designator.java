package main.ast.nodes;

import main.ast.nodes.expr.Expr;
import main.visitor.IVisitor;

public class Designator extends Node{
    private Expr e;
    private String idName;

    public Designator() {
    }

    public Expr getE() {
        return e;
    }

    public void setE(Expr e) {
        this.e = e;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

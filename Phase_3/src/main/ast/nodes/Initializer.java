package main.ast.nodes;

import main.ast.nodes.expr.Expr;
import main.visitor.IVisitor;

public class Initializer extends Node{
    private Expr e;
    private InitializerList il;

    public Initializer() {
    }

    public Expr getE() {
        return e;
    }

    public void setE(Expr e) {
        this.e = e;
    }

    public InitializerList getIl() {
        return il;
    }

    public void setIl(InitializerList il) {
        this.il = il;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

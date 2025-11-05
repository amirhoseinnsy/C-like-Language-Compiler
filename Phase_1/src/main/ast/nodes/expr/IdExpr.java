package main.ast.nodes.expr;

import main.visitor.IVisitor;

public class IdExpr extends Expr {
    public String name;
    public IdExpr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

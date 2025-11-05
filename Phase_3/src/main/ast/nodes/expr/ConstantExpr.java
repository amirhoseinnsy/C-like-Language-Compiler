package main.ast.nodes.expr;

import main.visitor.IVisitor;

public class ConstantExpr extends Expr {
    public String value;
    public ConstantExpr(String value) {
        this.value = value;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

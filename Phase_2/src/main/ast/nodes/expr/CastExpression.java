package main.ast.nodes.expr;

import main.visitor.IVisitor;

public class CastExpression extends Expr{
    private TypeName type;
    private CastExpression NestedCE;
    private Expr e;

    public CastExpression() { }

    public TypeName getType() {
        return type;
    }

    public void setType(TypeName type) {
        this.type = type;
    }

    public CastExpression getNestedCE() {
        return NestedCE;
    }

    public void setNestedCE(CastExpression nestedCE) {
        NestedCE = nestedCE;
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

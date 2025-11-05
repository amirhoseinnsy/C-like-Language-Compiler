package main.ast.nodes.expr;

import main.ast.nodes.InitializerList;
import main.visitor.IVisitor;

public class CompoundLiteralExpr extends Expr{
    private TypeName tn;
    private InitializerList initlst;
    public CompoundLiteralExpr() {
    }

    public TypeName getTn() {
        return tn;
    }

    public void setTn(TypeName tn) {
        this.tn = tn;
    }

    public InitializerList getInitlst() {
        return initlst;
    }

    public void setInitlst(InitializerList initlst) {
        this.initlst = initlst;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

}

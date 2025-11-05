package main.ast.nodes.expr;

import main.visitor.IVisitor;

import java.util.ArrayList;

public abstract class ExprList extends Expr{
    public ArrayList<Expr> exprs= new ArrayList<>();
    public ExprList(Expr expr) {
        exprs.add(expr);
    };
    public void addExpr(Expr expr) {
        exprs.add(expr);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

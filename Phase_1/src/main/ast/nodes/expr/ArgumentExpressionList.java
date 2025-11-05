package main.ast.nodes.expr;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class ArgumentExpressionList extends Expr{
    private ArrayList<Expr> exprlist = new ArrayList<>();
    public ArgumentExpressionList() {
    }

    public void addExpr(Expr expr) {
        this.exprlist.add(expr);
    }

    public ArrayList<Expr> getExprlist() {
        return exprlist;
    }

    public void setExprlist(ArrayList<Expr> exprlist) {
        this.exprlist = exprlist;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

package main.ast.nodes;

import main.ast.nodes.expr.Expr;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class ForExpression extends Node{
    private ArrayList<Expr> exprlist = new ArrayList<>();

    public ForExpression() {
    }

    public void addExpr(Expr e) {
        this.exprlist.add(e);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ArrayList<Expr> getExprlist() {
        return exprlist;
    }
}

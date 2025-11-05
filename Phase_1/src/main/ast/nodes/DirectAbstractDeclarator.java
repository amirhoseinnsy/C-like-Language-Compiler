package main.ast.nodes;

import main.ast.nodes.expr.Expr;
import main.visitor.IVisitor;

public class DirectAbstractDeclarator extends Node{
    private Expr e;
    private AbstractDeclarator ad;
    private ParameterList Pl;
    private DirectAbstractDeclarator dad;

    public DirectAbstractDeclarator() {
    }

    public Expr getE() {
        return e;
    }

    public void setE(Expr e) {
        this.e = e;
    }

    public AbstractDeclarator getAd() {
        return ad;
    }

    public void setAd(AbstractDeclarator ad) {
        this.ad = ad;
    }

    public ParameterList getPl() {
        return Pl;
    }

    public void setPl(ParameterList pl) {
        Pl = pl;
    }

    public DirectAbstractDeclarator getDad() {
        return dad;
    }

    public void setDad(DirectAbstractDeclarator dad) {
        this.dad = dad;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

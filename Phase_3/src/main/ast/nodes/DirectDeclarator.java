package main.ast.nodes;

import main.ast.nodes.expr.Expr;
import main.visitor.IVisitor;

public class DirectDeclarator extends Node{
    private String name;
    private Declarator dr;
    private Expr e;
    private DirectDeclarator dd;
    private ParameterList pl;
    private IdentifierList il;

    public DirectDeclarator() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Declarator getDr() {
        return dr;
    }

    public void setDr(Declarator dr) {
        this.dr = dr;
    }

    public DirectDeclarator getDd() {
        return dd;
    }

    public void setDd(DirectDeclarator dd) {
        this.dd = dd;
    }

    public ParameterList getPl() {
        return pl;
    }

    public void setPl(ParameterList pl) {
        this.pl = pl;
    }

    public IdentifierList getIl() {
        return il;
    }

    public Expr getE() {
        return e;
    }

    public void setE(Expr e) {
        this.e = e;
    }

    public void setIl(IdentifierList il) {
        this.il = il;
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

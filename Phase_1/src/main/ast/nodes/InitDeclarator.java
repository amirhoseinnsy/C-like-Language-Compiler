package main.ast.nodes;

import main.visitor.IVisitor;

public class InitDeclarator extends Node{
    private Declarator dr;
    private Initializer init;
    public InitDeclarator() {
    }

    public Declarator getDr() {
        return dr;
    }

    public void setDr(Declarator dr) {
        this.dr = dr;
    }

    public Initializer getInit() {
        return init;
    }

    public void setInit(Initializer init) {
        this.init = init;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

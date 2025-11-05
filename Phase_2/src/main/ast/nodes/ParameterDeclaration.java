package main.ast.nodes;

import main.visitor.IVisitor;

public class ParameterDeclaration extends Node{
    private DeclarationSpecifiers dss;
    private Declarator dr;
    private AbstractDeclarator ad;
    public ParameterDeclaration() {
    }

    public DeclarationSpecifiers getDss() {
        return dss;
    }

    public void setDss(DeclarationSpecifiers dss) {
        this.dss = dss;
    }

    public Declarator getDr() {
        return dr;
    }

    public void setDr(Declarator dr) {
        this.dr = dr;
    }

    public AbstractDeclarator getAd() {
        return ad;
    }

    public void setAd(AbstractDeclarator ad) {
        this.ad = ad;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

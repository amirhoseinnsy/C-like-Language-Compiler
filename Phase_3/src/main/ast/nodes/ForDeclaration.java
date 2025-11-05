package main.ast.nodes;

import main.visitor.IVisitor;

public class ForDeclaration extends Node{
    private DeclarationSpecifiers dss;
    private InitDeclaratorList idl;

    public ForDeclaration() {
    }

    public DeclarationSpecifiers getDss() {
        return dss;
    }

    public void setDss(DeclarationSpecifiers dss) {
        this.dss = dss;
    }

    public InitDeclaratorList getIdl() {
        return idl;
    }

    public void setIdl(InitDeclaratorList idl) {
        this.idl = idl;
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

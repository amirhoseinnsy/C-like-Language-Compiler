package main.ast.nodes;

import main.visitor.IVisitor;

public class Program extends Node{
    private TranslationUnit tu;
    public Program() {}

    public TranslationUnit getTu() {
        return tu;
    }

    public void setTu(TranslationUnit tu) {
        this.tu = tu;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }



}

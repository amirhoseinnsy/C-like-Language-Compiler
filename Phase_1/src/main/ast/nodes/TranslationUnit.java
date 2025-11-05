package main.ast.nodes;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class TranslationUnit extends Node {
    private ArrayList<ExternalDeclaration> EDS = new ArrayList<>();
    public TranslationUnit() { }
    public void addED(ExternalDeclaration ED) {
        this.EDS.add(ED);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ArrayList<ExternalDeclaration> getEDS() {
        return EDS;
    }
}

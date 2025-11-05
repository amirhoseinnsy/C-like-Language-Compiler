package main.ast.nodes;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class ParameterList extends Node{
    private ArrayList<ParameterDeclaration> pl = new ArrayList<>();
    public ParameterList() {
    }

    public void addPl(ParameterDeclaration pd) {
        this.pl.add(pd);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ArrayList<ParameterDeclaration> getPl() {
        return pl;
    }
}

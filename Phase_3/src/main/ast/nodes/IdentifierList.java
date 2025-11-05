package main.ast.nodes;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class IdentifierList extends Node{
    public ArrayList<String> identifierList = new ArrayList<>();
    public IdentifierList(String id) {
        this.identifierList.add(id);
    }
    public void addIdentifier(String id) {
        this.identifierList.add(id);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

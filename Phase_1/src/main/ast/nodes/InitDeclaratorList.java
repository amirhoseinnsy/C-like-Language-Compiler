package main.ast.nodes;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class InitDeclaratorList extends Node {
    private ArrayList<InitDeclarator> ids = new ArrayList<>();
    private ArrayList<Integer>  commaLines = new ArrayList<>();

    public InitDeclaratorList() {
    }

    public void addIds(InitDeclarator id) {
        this.ids.add(id);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ArrayList<InitDeclarator> getIds() {
        return ids;
    }

    public void setIds(ArrayList<InitDeclarator> ids) {
        this.ids = ids;
    }

    public ArrayList<Integer> getCommaLines() {
        return commaLines;
    }

    public void addCommaLine(int line) {
        commaLines.add(line);
    }
}

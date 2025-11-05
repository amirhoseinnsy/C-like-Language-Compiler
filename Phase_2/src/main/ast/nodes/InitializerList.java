package main.ast.nodes;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class InitializerList extends Node {
    private ArrayList<Designation> deslist = new ArrayList<>();
    private ArrayList<Initializer> initlist = new ArrayList<>();

    public InitializerList() {
    }

    public ArrayList<Designation> getDeslist() {
        return deslist;
    }

    public void addDes(Designation des) {
        this.deslist.add(des);
    }

    public ArrayList<Initializer> getInitlist() {
        return initlist;
    }

    public void addInit(Initializer init) {
        this.initlist.add(init);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

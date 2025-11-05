package main.ast.nodes;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class Designation extends Node {
    private ArrayList<Designator> dsrlist = new ArrayList<>();

    public Designation() {
    }

    public void addDsr(Designator dsr) {
        this.dsrlist.add(dsr);
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ArrayList<Designator> getDsrlist() {
        return dsrlist;
    }
}

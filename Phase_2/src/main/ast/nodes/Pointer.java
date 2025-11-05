package main.ast.nodes;

import main.visitor.IVisitor;

public class Pointer extends Node{
    private int level;
    public Pointer() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel() {
        this.level++;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

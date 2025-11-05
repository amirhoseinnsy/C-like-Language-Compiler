package main.ast.nodes.Stmt;

import main.ast.nodes.Node;

public abstract class Stmt extends Node {
    private int type = -1;

    public Stmt() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

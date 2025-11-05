package main.ast.nodes;

import main.visitor.IVisitor;

public class Declarator extends Node{
    private Pointer ptr;
    private DirectDeclarator dd;
    public Declarator() { }

    public Pointer getPtr() {
        return ptr;
    }

    public void setPtr(Pointer ptr) {
        this.ptr = ptr;
    }

    public DirectDeclarator getDd() {
        return dd;
    }

    public void setDd(DirectDeclarator dd) {
        this.dd = dd;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

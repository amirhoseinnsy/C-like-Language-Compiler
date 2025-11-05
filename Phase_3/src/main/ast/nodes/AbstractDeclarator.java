package main.ast.nodes;

import main.visitor.IVisitor;

public class AbstractDeclarator extends Node {
    private Pointer pointer;
    private DirectAbstractDeclarator dad;
    public AbstractDeclarator() {
    }

    public Pointer getPointer() {
        return pointer;
    }

    public void setPointer(Pointer pointer) {
        this.pointer = pointer;
    }

    public DirectAbstractDeclarator getDad() {
        return dad;
    }

    public void setDad(DirectAbstractDeclarator dad) {
        this.dad = dad;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

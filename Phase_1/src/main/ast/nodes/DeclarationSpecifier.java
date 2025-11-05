package main.ast.nodes;

import main.ast.nodes.Type;
import main.visitor.IVisitor;

public class DeclarationSpecifier extends Node{
    public Type type;
    public boolean IsConst;
    public DeclarationSpecifier() {
    }

    public Type getType() {
        return type;

    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isConst() {
        return IsConst;
    }

    public void setConst(boolean aConst) {
        IsConst = aConst;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

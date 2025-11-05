package main.ast.nodes;

import main.visitor.IVisitor;

public class AssignmentOperator extends Node {
    public String symbol;

    public AssignmentOperator(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}

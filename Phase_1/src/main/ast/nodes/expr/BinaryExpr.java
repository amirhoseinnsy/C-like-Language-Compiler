package main.ast.nodes.expr;

import main.visitor.IVisitor;

public class BinaryExpr extends Expr{
    private String name;
    private Expr operand1;
    private Expr operand2;

    public BinaryExpr(String name, Expr operand1, Expr operand2) {
        this.name = name;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public Expr getOperand1() {
        return operand1;
    }

    public Expr getOperand2() {
        return operand2;
    }
}

package main.ast.nodes.expr;

import main.ast.nodes.expr.UnaryOperator;
import main.visitor.IVisitor;

public class UnaryExpr extends Expr{
    private Expr operand;
    private UnaryOperator operator;


    public UnaryExpr() { }

    public Expr getOperand() {
        return operand;
    }

    public void setOperand(Expr operand) {
        this.operand = operand;
    }

    public UnaryOperator getOperator() {return operator;}

    public void setOperator(UnaryOperator operator) {
        this.operator = operator;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

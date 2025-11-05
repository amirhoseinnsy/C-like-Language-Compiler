package main.ast.nodes.Stmt;

import main.ast.nodes.ForCondition;
import main.ast.nodes.expr.Expr;
import main.symbolTable.SymbolTable;
import main.visitor.IVisitor;

public class IterationStatement extends Stmt{
    private Expr e;
    private Stmt stmt;
    private ForCondition fc;
    private int stmtIndex;
    public void setStmtIndex(int idx) { this.stmtIndex = idx; }
    public int getStmtIndex() { return stmtIndex; }
    private SymbolTable symbol_table;

    public SymbolTable getSymbol_table() {
        return symbol_table;
    }

    public void setSymbol_table(SymbolTable symbol_table) {
        this.symbol_table = symbol_table;
    }

    public IterationStatement() {
        this.setType(4);
    }

    public Expr getE() {
        return e;
    }

    public void setE(Expr e) {
        this.e = e;
    }

    public Stmt getStmt() {
        return stmt;
    }

    public void setStmt(Stmt stmt) {
        this.stmt = stmt;
    }

    public ForCondition getFc() {
        return fc;
    }

    public void setFc(ForCondition fc) {
        this.fc = fc;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

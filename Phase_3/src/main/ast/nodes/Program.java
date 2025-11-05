package main.ast.nodes;

import main.visitor.IVisitor;
import main.symbolTable.SymbolTable;

public class Program extends Node{
    private SymbolTable symbol_table;
    private TranslationUnit tu;
    public Program() {}

    public SymbolTable getSymbol_table() {
        return symbol_table;
    }

    public void setSymbol_table(SymbolTable symbol_table) {
        this.symbol_table = symbol_table;
    }

    public TranslationUnit getTu() {
        return tu;
    }

    public void setTu(TranslationUnit tu) {
        this.tu = tu;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }



}

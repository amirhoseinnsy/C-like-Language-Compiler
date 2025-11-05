package main.symbolTable.item;

import main.ast.nodes.*;
import main.symbolTable.SymbolTable;

import java.util.ArrayList;

public class FuncDecSymbolTableItem extends SymbolTableItem {
    public static final String START_KEY = "FuncDec_";

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FunctionDefinition getFuncDec() {
        return funcDec;
    }

    public void setFuncDec(FunctionDefinition funcDec) {
        this.funcDec = funcDec;
    }

    private FunctionDefinition funcDec;

    public FuncDecSymbolTableItem(FunctionDefinition funcDec) {
        this.funcDec = funcDec;
        this.tmp = true;

    }

    @Override
    public String getKey() {
        String ret = this.name;
        ret += "_" + types.size();
        return ret;
    }

}

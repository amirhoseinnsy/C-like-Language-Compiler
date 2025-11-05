package main.symbolTable.item;

import main.ast.nodes.*;
import main.symbolTable.SymbolTable;

import java.util.ArrayList;

public class FuncDecSymbolTableItem extends SymbolTableItem {
    public static final String START_KEY = "FuncDec_";
    private String name;

    public ArrayList<PremetiveType> getTypes() {
        return types;
    }

    public void addTypes(PremetiveType type) {
        this.types.add(type);
    }

    private ArrayList<PremetiveType> types = new ArrayList<>();

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
    }

    @Override
    public String getKey() {
        String ret = this.name + "_" + (types.size() - 1);
//        for (PremetiveType type : types) {
//            ret += "_" + type.getName();
//        }
        return ret;
    }

}

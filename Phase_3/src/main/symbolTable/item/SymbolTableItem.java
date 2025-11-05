package main.symbolTable.item;

import main.ast.nodes.PremetiveType;

import java.util.ArrayList;

public abstract class SymbolTableItem {
    public abstract String getKey();
    protected ArrayList<PremetiveType> types = new ArrayList<>();
    public String type;
    private PremetiveType out_type;
    protected boolean tmp;

    public PremetiveType getOut_type() {
        return out_type;
    }

    public ArrayList<PremetiveType> getTypes() {
        return types;
    }

    public void addTypes(PremetiveType type) {
        if (tmp) {
            this.out_type = type;
            tmp = false;
            return;
        }
        this.types.add(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

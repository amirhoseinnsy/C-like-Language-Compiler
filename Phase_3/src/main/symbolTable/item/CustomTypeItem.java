package main.symbolTable.item;

import main.ast.nodes.PremetiveType;

public class CustomTypeItem extends SymbolTableItem{
    private String name;
    private PremetiveType premetiveType;

    public PremetiveType getPremetiveType() {
        return premetiveType;
    }

    public void setPremetiveType(PremetiveType premetiveType) {
        this.premetiveType = premetiveType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomTypeItem(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return name;
    }

}

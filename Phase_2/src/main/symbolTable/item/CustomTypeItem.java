package main.symbolTable.item;

public class CustomTypeItem extends SymbolTableItem{
    private String name;
    public CustomTypeItem(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return name;
    }

}

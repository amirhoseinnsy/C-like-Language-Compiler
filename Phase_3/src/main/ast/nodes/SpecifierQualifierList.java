package main.ast.nodes;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class SpecifierQualifierList extends Type {
    private Type type;
    private SpecifierQualifierList sql;
    public SpecifierQualifierList() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SpecifierQualifierList getSql() {
        return sql;
    }

    public void setSql(SpecifierQualifierList sql) {
        this.sql = sql;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

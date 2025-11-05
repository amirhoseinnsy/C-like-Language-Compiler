package main.ast.nodes.expr;

import main.ast.nodes.AbstractDeclarator;
import main.ast.nodes.SpecifierQualifierList;
import main.visitor.IVisitor;

public class TypeName extends Expr{
    private SpecifierQualifierList sql;
    private AbstractDeclarator ad;
    public TypeName() {
    }

    public SpecifierQualifierList getSql() {
        return sql;
    }

    public void setSql(SpecifierQualifierList sql) {
        this.sql = sql;
    }

    public AbstractDeclarator getAd() {
        return ad;
    }

    public void setAd(AbstractDeclarator ad) {
        this.ad = ad;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

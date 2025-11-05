package main.ast.nodes;
import main.ast.nodes.Type;
import main.visitor.IVisitor;

public class CustomType extends Type{
    public String identifier;
    public CustomType(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

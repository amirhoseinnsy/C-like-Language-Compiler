package main.ast.nodes.Stmt;

import main.ast.nodes.Declaration;
import main.visitor.IVisitor;

public class BlockItem extends Stmt{
    private Stmt stmt;
    private Declaration declaration;

    public BlockItem() {
    }

    public Stmt getStmt() {
        return stmt;
    }

    public void setStmt(Stmt stmt) {
        this.stmt = stmt;
    }

    public Declaration getDeclaration() {
        return declaration;
    }

    public void setDeclaration(Declaration declaration) {
        this.declaration = declaration;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

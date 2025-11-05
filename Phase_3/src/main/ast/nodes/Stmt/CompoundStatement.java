package main.ast.nodes.Stmt;

import main.visitor.IVisitor;

import java.util.ArrayList;

public class CompoundStatement extends Stmt {
    private ArrayList<BlockItem> bilist = new ArrayList<>();

    public void setBilist(ArrayList<BlockItem> bilist) {
        this.bilist = bilist;
    }

    public CompoundStatement() {
        this.setType(1);
    }

    public void addBi(BlockItem bi) {
        this.bilist.add(bi);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ArrayList<BlockItem> getBilist() {
        return bilist;
    }
}

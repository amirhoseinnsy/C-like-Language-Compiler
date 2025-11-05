package main.typeErrors;

public class NonSameOperands extends TypeError {
    public NonSameOperands(int line, String lhsType, String rhsType) {
        super(line, "cannot assign " + rhsType + " to " + lhsType);
    }
}

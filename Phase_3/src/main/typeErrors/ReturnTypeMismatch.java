package main.typeErrors;

public class ReturnTypeMismatch extends TypeError {
    public ReturnTypeMismatch(int line,
                              String expectedType,
                              String actualType) {
        super(line, "function expects " + expectedType
                + ", got " + actualType);
    }
}

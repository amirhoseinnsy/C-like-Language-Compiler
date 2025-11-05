package main.typeErrors;

public class ArgumentTypeMismatch extends TypeError {
    // wrong count
    public ArgumentTypeMismatch(int line, int expectedCount, int actualCount) {
        super(line, "wrong number of args: expected "
                + expectedCount + ", got " + actualCount);
    }
    // wrong type on one parameter
    public ArgumentTypeMismatch(int line, int paramIndex,
                                String expectedType, String actualType) {
        super(line, "on parameter " + paramIndex
                + " expected " + expectedType
                + ", got " + actualType);
    }
}

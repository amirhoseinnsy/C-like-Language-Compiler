package main.typeErrors;

public abstract class TypeError {
    private final int line;
    private final String message;

    public TypeError(int line, String message) {
        this.line = line;
        this.message = message;
    }

    public int getLine() { return line; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "Line:" + line + " -> " +
                this.getClass().getSimpleName() +
                ": " + message;
    }
}

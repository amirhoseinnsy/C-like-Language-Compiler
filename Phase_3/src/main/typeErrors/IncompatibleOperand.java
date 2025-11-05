package main.typeErrors;

public class IncompatibleOperand extends TypeError {
    public int line;

    public IncompatibleOperand(int line, String message, int line1) {
        super(line, message);
        this.line = line1;
    }
    public String getErrorMessage(){return "Line:" + this.line + "-> Incompatible operand(s)";}

}

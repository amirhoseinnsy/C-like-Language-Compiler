package main.visitor;


import main.symbolTable.SymbolTable;
import main.symbolTable.exceptions.ItemAlreadyExistsException;
import main.symbolTable.exceptions.ItemNotFoundException;
import main.symbolTable.item.CustomTypeItem;
import main.symbolTable.item.FuncDecSymbolTableItem;
import main.ast.nodes.*;
import main.ast.nodes.expr.*;
import main.ast.nodes.Stmt.*;
import main.typeErrors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ExpressionTypeEvaluator extends Visitor<String>{
    private SymbolTable currentSymbolTable;
    private ArrayList<TypeError> type_errors = new ArrayList<>();
    ArrayList<String> ignoreList = new ArrayList<>(Arrays.asList(
            // C language keywords
            "auto", "break", "case", "char", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extern", "float", "for", "goto",
            "if", "inline", "int", "long", "register",
            "restrict", "return", "short", "signed", "sizeof",
            "static", "struct", "switch", "typedef", "union",
            "unsigned", "void", "volatile", "while",
            "_Alignas", "_Alignof", "_Atomic", "_Bool", "_Complex",
            "_Generic", "_Imaginary", "_Noreturn", "_Static_assert", "_Thread_local",

            // stdio.h functions
            "printf", "scanf", "fprintf", "fscanf", "sprintf",
            "snprintf", "gets", "puts", "putchar", "getchar",
            "fopen", "fclose", "fread", "fwrite", "fseek",
            "ftell", "rewind", "fflush", "perror", "remove",
            "rename", "setbuf", "setvbuf",

            // stdlib.h functions
            "malloc", "calloc", "realloc", "free", "exit",
            "atoi", "atof", "atol", "system", "abort",
            "rand", "srand", "qsort", "bsearch",

            // string.h functions
            "strlen", "strcpy", "strncpy", "strcat", "strncat",
            "strcmp", "strncmp", "strchr", "strrchr", "strstr",
            "memset", "memcpy", "memmove", "memcmp",

            // ctype.h functions
            "isalnum", "isalpha", "isdigit", "isspace", "islower",
            "isupper", "tolower", "toupper",

            // math.h functions
            "abs", "fabs", "ceil", "floor", "pow",
            "sqrt", "sin", "cos", "tan", "log",
            "exp", "fmod",

            // macros and constants
            "NULL", "EOF", "EXIT_SUCCESS", "EXIT_FAILURE",
            "SIZE_MAX", "INT_MAX", "INT_MIN", "UINT_MAX"
    ));

    ExpressionTypeEvaluator(ArrayList<TypeError> type_errors){
        this.type_errors = type_errors;
    }


    public void setCurrentSymbolTable(SymbolTable currentSymbolTable) {
        this.currentSymbolTable = currentSymbolTable;
    }

    private boolean isNumericType(String type) {
        return type.equals("char") ||
                type.equals("short") ||
                type.equals("int") ||
                type.equals("long") ||
                type.equals("float") ||
                type.equals("double");
    }

    private String getWiderNumericType(String t1, String t2) {
        int rank1 = numericRank(t1);
        int rank2 = numericRank(t2);
        int maxRank = Math.max(rank1, rank2);
        return numericTypeByRank(maxRank);
    }

    private int numericRank(String type) {
        switch (type) {
            case "char": return 1;
            case "short": return 2;
            case "int": return 3;
            case "long": return 4;
            case "float": return 5;
            case "double": return 6;
            default: return -1;
        }
    }

    private String numericTypeByRank(int rank) {
        switch (rank) {
            case 1: return "char";
            case 2: return "short";
            case 3: return "int";
            case 4: return "long";
            case 5: return "float";
            case 6: return "double";
            default: return "none";
        }
    }

    private boolean isIntegerType(String type) {
        return type.equals("Char") ||
                type.equals("Short") ||
                type.equals("Int") ||
                type.equals("Long");
    }



    @Override
    public String visit(UnaryExpr unaryExpr) {
        String operand_type = unaryExpr.getOperand().accept(this);
        String operator = unaryExpr.getOperator().getName();
        String final_type = "none";

        if (operator.equals("!")) {

            if (operand_type.equals("bool")) {
                final_type = "bool";
            }
        } else if (operator.equals("~")) {

            if (isIntegerType(operand_type)) {
                final_type = operand_type;
            }
        } else if (operator.equals("+") || operator.equals("-") || operator.equals("++") || operator.equals("--")) {

            if (isNumericType(operand_type)) {
                final_type = operand_type;
            }
        }

        if (final_type.equals("none")) {
        }

        return final_type;
    }

    @Override
    public String visit(BinaryExpr binaryExpr) {
        String l_op_type = binaryExpr.getOperand1().accept(this);
        String r_op_type = binaryExpr.getOperand2().accept(this);
        String final_type = "none";

        ArrayList<String> arithmeticOperators = new ArrayList<>(Arrays.asList(
                "*", "/", "%", "+", "-", "=", "*=", "/=", "%=", "+=", "-=", "<<=", ">>="
        ));
        if (arithmeticOperators.contains(binaryExpr.getName())) {
            if (isNumericType(l_op_type) && isNumericType(r_op_type)) {
                final_type = getWiderNumericType(l_op_type, r_op_type);
            }
        }

        ArrayList<String> logicalOperators = new ArrayList<>(Arrays.asList(
                "&", "|", "&&", "||", "^", "&=", "^=", "|="
        ));
        if (logicalOperators.contains(binaryExpr.getName())) {
            if (l_op_type.equals("bool") && r_op_type.equals("bool")) {
                final_type = "bool";
            }
        }

        ArrayList<String> comparisonOperators = new ArrayList<>(Arrays.asList(
                "<", "<=", ">", ">=", "==", "!="
        ));
        if (comparisonOperators.contains(binaryExpr.getName())) {
            if ((isNumericType(l_op_type) || l_op_type.equals("bool")) &&
                    (isNumericType(r_op_type) || r_op_type.equals("bool"))) {
                final_type = "bool";
            }
        }

        if (final_type.equals("none")) {
            System.out.println("Line:" + binaryExpr.getLine() + " -> type mismatch in expression");
        }

        return final_type;
    }

    public String visit(ConstantExpr constantExpr) {
        String value = constantExpr.getValue().toLowerCase();

        if (value.contains(".") || value.endsWith("f") || value.endsWith("d")) {
            return "float";
        }

        if (value.endsWith("l")) {
            return "long";
        }

        try {
            int intValue = Integer.parseInt(value);
            if (intValue >= Short.MIN_VALUE && intValue <= Short.MAX_VALUE) {
                return "short";
            } else {
                return "int";
            }
        } catch (NumberFormatException e1) {
            try {
                Long.parseLong(value);
                return "long";
            } catch (NumberFormatException e2) {
                if (value.length() == 3 && value.startsWith("'") && value.endsWith("'")) {
                    return "char";
                }
            }
        }

        return "none";
    }


    public String visit(IdExpr idExpr) {
        String type = "none";
        try {
            if (!ignoreList.contains(idExpr.getName())) {
                CustomTypeItem customTypeItem = (CustomTypeItem) SymbolTable.top.getItem(idExpr.getName());
                type = customTypeItem.getType();
            }
        } catch (ItemNotFoundException e) { }
        return type;
    }

    public String visit(StringLiteralExpr stringLiteralExpr) {
        return "string";
    }

    public String visit(Expr expr){
        return "none";
    }

}

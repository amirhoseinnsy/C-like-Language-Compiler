package main.visitor;

import main.symbolTable.SymbolTable;
import main.symbolTable.exceptions.ItemAlreadyExistsException;
import main.symbolTable.exceptions.ItemNotFoundException;
import main.symbolTable.item.CustomTypeItem;
import main.symbolTable.item.FuncDecSymbolTableItem;
//import main.symbolTable.item.VarDecSymbolTableItem;
import main.ast.nodes.*;
import main.ast.nodes.expr.*;
import main.ast.nodes.Stmt.*;
import main.symbolTable.item.SymbolTableItem;
import main.typeErrors.TypeError;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 *   Main Changes:
 *       1.create a SymbolTable class to act as our symbol table
 *       2.create some symbolTableItems as the different nodes which are going to be stored in the SymbolTable
 *       3.create a new visitor as our NameAnalyzer
 *       4.add a symbolTable field in program, main, and func_dec AST nodes to store the corresponding symbol table
 * */



public class TypeChecker extends Visitor<Void>{
    private String currentFunctionName;
    private boolean isInFunction = false;
    private FuncDecSymbolTableItem cuurent_func_dec;
    private int size_of_exprlist = 1;
    private ArrayList<String> functions_names = new ArrayList<>();
    private boolean isInDeclaration = false;
    private ArrayList<String> variable_names = new ArrayList<>();
    private boolean isInParameterlist = false;
    private int parameter_list_size = 0;
    private boolean isRet = false;
    private int cuurent_function_line = 0;
    private boolean isMain = false;
    private ArrayList<String> MainCallFunctions = new ArrayList<>();
    private Map<String, Object> func_parameters = new HashMap<>();
    private Map<String, String> func_out_type = new HashMap<>();
    private Map<String, Object> func_lines = new HashMap<>();
    private Map<String, Object> func_blocks = new HashMap<>();
    private boolean isConstant = false;
    private BlockItem currentBlockItem;
    public boolean isFine = true;
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
    private ArrayList<TypeError> type_errors = new ArrayList<>();
    private ExpressionTypeEvaluator expr_type_evaluator = new ExpressionTypeEvaluator(type_errors);
    private ArrayList<Expr> argumetns_function = new ArrayList<>();
    private PremetiveType current_premetive_type;
    private String init_ret_type;
    private boolean in_parameter = false;
    private ArrayList<CustomTypeItem> parameters = new ArrayList<>();

    private String extractFunctionName(Declarator declarator) {
        DirectDeclarator dd = declarator.getDd();
        while (dd != null) {
            if (dd.getName() != null) {
                return dd.getName();
            }
            Declarator inner = dd.getDr();
            if (inner != null) {
                dd = inner.getDd();
            } else {
                dd = dd.getDd();
            }
        }
        return "UNKNOWN";
    }

    private void extractElements(ArgumentExpressionList argumentExpressionList) {
        for (Expr expr : argumentExpressionList.getExprlist()) {
            if (expr instanceof ArgumentExpressionList) {
                extractElements((ArgumentExpressionList) expr);
            }
            else {
                argumetns_function.add(expr);
            }
        }
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
        // Define rank for promotion
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
            default: return -1; // Invalid
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
    public Void visit(Program program) {
        SymbolTable.top = new SymbolTable();
        SymbolTable.root = SymbolTable.top;

        program.setSymbol_table(SymbolTable.top);

        TranslationUnit translationUnit = program.getTu();
        if (translationUnit != null) {
            translationUnit.accept(this);
        }

        return null;
    }
    public Void visit(ArgumentExpressionList ael) {
        ArrayList<Expr> list = ael.getExprlist();
        size_of_exprlist = list.size();

        if (list.get(size_of_exprlist - 1) instanceof IdExpr) {
            if (functions_names.contains(((IdExpr) list.get(size_of_exprlist - 1)).getName())) {
                String name = ((IdExpr) list.get(size_of_exprlist - 1)).getName();

                try {
                    argumetns_function = new ArrayList<>();
                    extractElements(ael);
                    ArrayList<PremetiveType> types = SymbolTable.top.getItem(name + "_" + (argumetns_function.size() - 1)).getTypes();
                    expr_type_evaluator.setCurrentSymbolTable(SymbolTable.top);
                    int index = 0;

                    if (argumetns_function.size() - 1 == 0 && types.size() == 0) { }
                    else {
                        for (Expr e : argumetns_function) {
                            String exprtype = e.accept(this.expr_type_evaluator);
                            if (numericRank(types.get(index).getName())  < numericRank(exprtype)) {
                                System.out.println("Line:" + e.getLine() + " -> argument type mismatch");
                                break;
                            }
                            index += 1;
                            if (index == argumetns_function.size() - 1) { break; }
                        }
                    }

                } catch (ItemNotFoundException e) {
                }
            }
        }

        return null;
    }

    public Void visit(AssignExpr assignExpr) {
        Expr e1 = assignExpr.getE1();
        Expr e2 = assignExpr.getE2();

        if (e1 != null) {
            e1.accept(this);
        }
        if (e2 != null) {
            e2.accept(this);
        }
        return null;
    }
    public Void visit(BinaryExpr binaryExpr) {
        Expr left  = binaryExpr.getOperand1();
        Expr right = binaryExpr.getOperand2();

        if (left  != null) left.accept(this);
        if (right != null) right.accept(this);

        return null;
    }
    public Void visit(BracketExpr bracketExpr) {
        Expr e1 = bracketExpr.getE1();
        Expr e2 = bracketExpr.getE2();

        if (e1 != null) {
            e1.accept(this);
        }
        if (e2 != null) {
            e2.accept(this);
        }

        return null;
    }
    public Void visit(CastExpression castExpression) {
        TypeName typeName = castExpression.getType();
        CastExpression castExpression_ = castExpression.getNestedCE();
        Expr e = castExpression.getE();

        if (typeName != null) {
            typeName.accept(this);
        }
        if (castExpression_ != null){
            castExpression_.accept(this);
        }
        if (e != null) {
            e.accept(this);
        }

        return null;
    }
    public Void visit(CompoundLiteralExpr compoundLiteralExpr) {
        TypeName typeName = compoundLiteralExpr.getTn();

        if (typeName != null) {
            typeName.accept(this);
        }

        return null;
    }
    public Void visit(ConstantExpr constantExpr) {
        isConstant = true;
        return null;
    }
    public Void visit(ExprList exprList) {
        return null;
    }

    public Void visit(Identifier identifier) {
        return null;
    }
    public Void visit(IdExpr idExpr) {
        try {
            if (!ignoreList.contains(idExpr.getName())) {
                if (functions_names.contains(idExpr.getName())) {

                    if (isMain) {
                        MainCallFunctions.add(idExpr.getName());
                    }
                }
                else {
                    SymbolTable.top.getItem(idExpr.getName());
                }
            }
        } catch (ItemNotFoundException e) {
            isFine = false;
            System.out.println("Line:" + idExpr.getLine() + "-> "  + idExpr.getName() + " not declared");
        }
        return null;
    }
    public Void visit(IfExpr ifExpr) {
        Expr e1 = ifExpr.getE1();
        Expr e2 = ifExpr.getE2();
        Expr e3 = ifExpr.getE3();

        if (e1 != null) {
            e1.accept(this);
        }
        if (e2 != null){
            e2.accept(this);
        }
        if (e3 != null){
            e3.accept(this);
        }

        return null;
    }
    public Void visit(StringLiteralExpr stringLiteralExpr) {
        return null;
    }
    public Void visit(TypeName typeName) {
        SpecifierQualifierList specifierQualifierList = typeName.getSql();
        AbstractDeclarator abstractDeclarator = typeName.getAd();

        if (specifierQualifierList != null) {
            specifierQualifierList.accept(this);
        }
        if (abstractDeclarator != null) {
            abstractDeclarator.accept(this);
        }

        return null;
    }
    public Void visit(UnaryExpr unaryExpr) {
        UnaryOperator opNode = unaryExpr.getOperator();

        Expr operand = unaryExpr.getOperand();
        if (operand != null) {
            operand.accept(this);
        }
        return null;
    }
    public Void visit(UnaryOperator unaryOperator) {
        return null;
    }
    public Void visit(BlockItem blockItem) {
        Stmt stmt = blockItem.getStmt();
        Declaration declaration = blockItem.getDeclaration();

        if (stmt != null) stmt.accept(this);
        if (declaration != null) declaration.accept(this);
        current_premetive_type = new PremetiveType("void");
        return null;
    }
    public Void visit(BreakState breakState) {
        return null;
    }
    public Void visit(CompoundStatement compoundStatement) {
        for (BlockItem blockItem : compoundStatement.getBilist()) {
            blockItem.accept(this);
        }
        func_blocks.put(currentFunctionName, compoundStatement.getBilist().size());

        return null;
    }
    public Void visit(ContinueState continueState) {
        return null;
    }
    public Void visit(ExpressionStatement stmt) {
        if (stmt.getE() != null) stmt.getE().accept(this);
        return null;
    }
    public Void visit(IterationStatement stmt) {
        Expr cond    = stmt.getE();
        ForCondition fc = stmt.getFc();

        Stmt body = stmt.getStmt();

        SymbolTable loop_symbol_table = new SymbolTable(SymbolTable.top);
        stmt.setSymbol_table(loop_symbol_table);
        SymbolTable.push(loop_symbol_table);

        if (fc  != null)      fc.accept(this);
        if (body != null)     body.accept(this);

        SymbolTable.pop();

        return null;
    }
    public Void visit(SelectionStatement ss) {
        Expr cond   = ss.getE();
        Stmt thenBlock = ss.getS1();

        SymbolTable if_symbol_table = new SymbolTable(SymbolTable.top);
        ss.setSymbol_table(if_symbol_table);
        SymbolTable.push(if_symbol_table);

        if (thenBlock != null)    thenBlock.accept(this);
        if (ss.getS2()   != null) ss.getS2().accept(this);

        SymbolTable.pop();
        return null;
    }
    public Void visit(JumpStatement stmt) {
        Expr expr = stmt.getE();
        expr_type_evaluator.setCurrentSymbolTable(SymbolTable.top);

        isRet = true;
        String retType = "void";
        if (expr != null) {
            retType = expr.accept(expr_type_evaluator);
        }
        if ((retType == "void" || func_out_type.get(currentFunctionName) == "void") && func_out_type.get(currentFunctionName) != retType) {
            System.out.println("Line:" + stmt.getLine() + " -> return type mismatch");
        }
        else if (numericRank(func_out_type.get(currentFunctionName)) < numericRank(retType)) {
            System.out.println("Line:" + stmt.getLine() + " -> return type mismatch");
        }

        return null;
    }
    public Void visit(ReturnState returnState) {
        Expr e = returnState.getE();
        expr_type_evaluator.setCurrentSymbolTable(SymbolTable.top);

        String retType = e.accept(expr_type_evaluator);
        if (!func_out_type.get(currentFunctionName).equals(retType)) {
            System.out.println("Line:" + returnState.getLine() + " -> return type mismatch");
        }

        return null;
    }
    public Void visit(AbstractDeclarator abstractDeclarator) {
        Pointer pointer = abstractDeclarator.getPointer();
        DirectAbstractDeclarator directAbstractDeclarator = abstractDeclarator.getDad();

        if (pointer != null) {
            pointer.accept(this);
        }
        if (directAbstractDeclarator != null) {
            directAbstractDeclarator.accept(this);
        }

        return null;
    }
    public Void visit(AssignmentOperator assignmentOperator) {
        return null;
    }
    public Void visit(CustomType customType) {
        if (isInDeclaration || isInParameterlist) {
            variable_names.add(customType.identifier);
            CustomTypeItem  customTypeItem = new CustomTypeItem(customType.identifier);
            customTypeItem.setType(current_premetive_type.getName());
            try {
                if (in_parameter) {
                    parameters.add(customTypeItem);
                }
                else {
                    SymbolTable.top.put(customTypeItem);
                }
            } catch (ItemAlreadyExistsException e) { }
        }
        return null;
    }
    public Void visit(Declaration declaration) {
        InitDeclaratorList initDeclaratorList = declaration.getIdl();
        DeclarationSpecifiers declarationSpecifiers = declaration.getDss();
        isInDeclaration = true;

        if (declarationSpecifiers != null) {
            declarationSpecifiers.accept(this);
        }
        if (initDeclaratorList != null) {
            initDeclaratorList.accept(this);
        }


        if (numericRank(current_premetive_type.getName()) < numericRank(init_ret_type)) {
            System.out.println("Line:" + declaration.getLine() + " -> type mismatch in expression");
        }

        isInDeclaration = false;
        return null;
    }
    public Void visit(DeclarationList declarationList) {
        for (Declaration declaration : declarationList.getDeclarations()) {
            if (declaration != null) {
                declaration.accept(this);
            }
        }
        return null;
    }
    public Void visit(DeclarationSpecifier declarationSpecifier) {
        Type type = declarationSpecifier.getType();
        if (type != null) {
            type.accept(this);
        }
        return null;
    }
    public Void visit(DeclarationSpecifiers declarationSpecifiers) {
        for (DeclarationSpecifier declarationSpecifier : declarationSpecifiers.getDs()) {
            if (declarationSpecifier != null) {
                declarationSpecifier.accept(this);
            }
        }

        return null;
    }
    public Void visit(Declarator declarator) {
        Pointer pointer = declarator.getPtr();
        DirectDeclarator directDeclarator = declarator.getDd();

        if (pointer != null) {
            pointer.accept(this);
        }
        if (directDeclarator != null) {
            directDeclarator.accept(this);
        }

        return null;
    }
    public Void visit(Designation designation) {
        for (Designator designator : designation.getDsrlist()) {
            if (designator != null) {
                designator.accept(this);
            }
        }

        return null;
    }
    public Void visit(Designator designator) {
        Expr e = designator.getE();
        if (e != null) {
            e.accept(this);
        }

        return null;
    }
    public Void visit(DirectAbstractDeclarator directAbstractDeclarator) {
        Expr expr = directAbstractDeclarator.getE();
        AbstractDeclarator abstractDeclarator = directAbstractDeclarator.getAd();
        ParameterList parameterList = directAbstractDeclarator.getPl();
        DirectAbstractDeclarator directAbstractDeclarator_ = directAbstractDeclarator.getDad();

        if (expr != null) {
            expr.accept(this);
        }
        if (abstractDeclarator != null) {
            abstractDeclarator.accept(this);
        }
        if (parameterList != null) {
            parameterList.accept(this);
        }
        if (directAbstractDeclarator_ != null) {
            directAbstractDeclarator_.accept(this);
        }

        return null;
    }
    public Void visit(DirectDeclarator directDeclarator) {
        Declarator declarator = directDeclarator.getDr();
        DirectDeclarator directDeclarator_ = directDeclarator.getDd();
        Expr expr = directDeclarator.getE();
        ParameterList parameterList = directDeclarator.getPl();
        IdentifierList identifierList = directDeclarator.getIl();
        String name = directDeclarator.getName();
        if (name != null && !functions_names.contains(name)) {
            CustomTypeItem  customTypeItem = new CustomTypeItem(name);
            customTypeItem.setType(current_premetive_type.getName());
            try {
                SymbolTable.top.put(customTypeItem);
            } catch (ItemAlreadyExistsException e) { }
        }

        if (declarator != null) {
            declarator.accept(this);
        }
        if (directDeclarator_ != null) {
            directDeclarator_.accept(this);
        }
        if (expr != null) {
            expr.accept(this);
        }
        if (parameterList != null) {
            parameterList.accept(this);
        }
        if (identifierList != null) {
            identifierList.accept(this);
        }

        return null;
    }
    public Void visit(ExternalDeclaration externalDeclaration) {
        FunctionDefinition functionDefinition = externalDeclaration.getFD();
        Declaration declaration = externalDeclaration.getDeclaration();

        if (functionDefinition != null) {
            functionDefinition.accept(this);
        }
        if (declaration != null) {
            declaration.accept(this);
        }



        return null;
    }
    public Void visit(ForCondition forCondition) {
        ForDeclaration forDeclaration = forCondition.getFD();
        Expr expr = forCondition.getE();
        ForExpression forExpression1 = forCondition.getFE1();
        ForExpression forExpression2 = forCondition.getFE2();

        if (forDeclaration != null) {
            forDeclaration.accept(this);
        }
        if (expr != null) {
            expr.accept(this);
        }
        if (forExpression1 != null) {
            forExpression1.accept(this);
        }
        if (forExpression2 != null) {
            forExpression2.accept(this);
        }

        return null;
    }
    public Void visit(ForDeclaration forDeclaration) {
        InitDeclaratorList initDeclaratorList = forDeclaration.getIdl();
        DeclarationSpecifiers declarationSpecifiers = forDeclaration.getDss();

        if (initDeclaratorList != null) {
            initDeclaratorList.accept(this);
        }
        if (declarationSpecifiers != null) {
            declarationSpecifiers.accept(this);
        }

        return null;
    }
    public Void visit(ForExpression forExpression) {
        for (Expr expr : forExpression.getExprlist()) {
            if (expr != null) {
                expr.accept(this);
            }
        }

        return null;
    }
    public Void visit(FunctionDefinition functionDefinition) {
        CompoundStatement body = functionDefinition.getCompoundStatement();
        Declarator declarator = functionDefinition.getDeclarator();
        DeclarationSpecifiers declarationSpecifiers = functionDefinition.getDeclarationSpecifiers();
        String name = extractFunctionName(declarator);
        currentFunctionName = name;
        if (!functions_names.contains(name)) {
            functions_names.add(name);
        }
        if (name.equals("main")) {
            isMain = true;
        }
        isInFunction = true;
        cuurent_function_line = functionDefinition.getLine();
        func_lines.put(name, functionDefinition.getLine());

        FuncDecSymbolTableItem func_dec_item = new FuncDecSymbolTableItem(functionDefinition);
        func_dec_item.setName(name);
        cuurent_func_dec = func_dec_item;

        parameters = new ArrayList<>();
        in_parameter = true;

        declarationSpecifiers.accept(this);
        declarator.accept(this);

        in_parameter = true;

        try {
            SymbolTable.top.put(func_dec_item);
        } catch (ItemAlreadyExistsException e) {
            functions_names.add(name);
        }

        SymbolTable func_dec_symbol_table = new SymbolTable(SymbolTable.top);
        functionDefinition.setSymbol_table(func_dec_symbol_table);
        SymbolTable.push(func_dec_symbol_table);
        func_out_type.put(currentFunctionName, func_dec_item.getOut_type().getName());

        for (CustomTypeItem customType : parameters) {
            try {
                SymbolTable.top.put(customType);
            } catch (ItemAlreadyExistsException e) {}
        }

        body.accept(this);

        SymbolTable.pop();
        isInFunction = false;
        isMain = false;
        return null;
    }
    public Void visit(IdentifierList identifierList) {
        return null;
    }
    public Void visit(InitDeclarator initDeclarator) {
        Initializer initializer = initDeclarator.getInit();

        if (initializer != null) {
            initializer.accept(this);
        }
        initDeclarator.getDr().accept(this);

        return null;
    }
    public Void visit(InitDeclaratorList idl) {
        for (InitDeclarator decl : idl.getIds()) {
            decl.accept(this);
        }
        return null;
    }
    public Void visit(Initializer initializer) {
        Expr expr = initializer.getE();


        try {
            if (expr instanceof ArgumentExpressionList) {
                ArrayList<Expr> list = ((ArgumentExpressionList) expr).getExprlist();
                String name = ((IdExpr) list.get(size_of_exprlist - 1)).getName();
                argumetns_function = new ArrayList<>();
                extractElements((ArgumentExpressionList) expr);
                init_ret_type = SymbolTable.top.getItem(name + "_" + (argumetns_function.size() - 1)).getOut_type().getName();

            } else {
                expr_type_evaluator.setCurrentSymbolTable(SymbolTable.top);
                if (expr != null) {
                    String retType = expr.accept(expr_type_evaluator);
                    init_ret_type = retType;
                }
            }

        } catch (ItemNotFoundException e) { }

        InitializerList il = initializer.getIl();
        if (il != null) {
            il.accept(this);
        }
        return null;
    }
    public Void visit(ParameterDeclaration parameterDeclaration) {
        Declarator declarator = parameterDeclaration.getDr();
        AbstractDeclarator abstractDeclarator = parameterDeclaration.getAd();


        if (declarator != null) {
            declarator.accept(this);
        }
        if (abstractDeclarator != null) {
            abstractDeclarator.accept(this);
        }
        parameterDeclaration.getDss().accept(this);

        return null;
    }
    public Void visit(ParameterList parameterList) {
        isInParameterlist = true;
        for (ParameterDeclaration parameterDeclaration : parameterList.getPl()) {
            parameterDeclaration.accept(this);
        }

        parameter_list_size = parameterList.getPl().size();
        func_parameters.put(currentFunctionName, parameterList.getPl().size());
        isInParameterlist = false;
        return null;
    }
    public Void visit(Pointer pointer) {
        return null;
    }
    public Void visit(PremetiveType premetiveType) {
        if (isInFunction) {
            cuurent_func_dec.addTypes(premetiveType);
        }
        current_premetive_type = premetiveType;
        return null;
    }
    public Void visit(SpecifierQualifierList specifierQualifierList) {
        Type type = specifierQualifierList.getType();
        SpecifierQualifierList specifierQualifierList_ = specifierQualifierList.getSql();

        if (type != null) {
            type.accept(this);
        }
        if (specifierQualifierList_ != null) {
            specifierQualifierList_.accept(this);
        }

        return null;
    }
    public Void visit(Symbol symbol) {
        return null;
    }
    public Void visit(TranslationUnit translationUnit) {
        for (ExternalDeclaration externalDeclaration : translationUnit.getEDS()) {
            externalDeclaration.accept(this);
        }

        return null;
    }
    public Void visit(Type type) {
        return null;
    }
    public Void visit(InitializerList initializerList) {
        if (initializerList.getDeslist() != null) {
            for (Designation designation : initializerList.getDeslist()) {
                designation.accept(this);
            }
        }
        for (Initializer initializer : initializerList.getInitlist()) {
            initializer.accept(this);
        }

        return null;
    }
}

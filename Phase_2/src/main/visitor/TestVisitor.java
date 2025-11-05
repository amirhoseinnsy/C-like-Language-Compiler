package main.visitor;

import main.ast.nodes.*;
import main.ast.nodes.expr.*;
import main.ast.nodes.Stmt.*;

import java.awt.*;
import java.util.ArrayList;

/*GOALs:
 *   1. print out scope changes each time a new scope starts
 *   2. print the identifier if it is initialized
 *   3. print the identifier if it is used
 *   4. print out the name of the function when it is defined
 *   5. print out the name of the function when it is used
 *
 * */


public class TestVisitor extends Visitor<Void>{
    private int stmtCount = 0;
    private int compoundDepth = 0;
    private int selectionStmtCount = 0;
    private String currentFunctionName = "";
    private int currentFunctionLine = -1;
    private int currentFunctionStmtCount = 0;
    private int selectionDepth = 0;

    private int extractFirstLineOfStmt(Stmt stmt) {
        if (stmt instanceof CompoundStatement cs && !cs.getBilist().isEmpty()) {
            BlockItem item = cs.getBilist().get(0);
            if (item.getStmt() != null) return extractFirstLineOfStmt(item.getStmt());
            if (item.getDeclaration() != null) return item.getDeclaration().getLine();
        } else if (stmt instanceof ExpressionStatement es && es.getE() != null) {
            return es.getE().getLine();
        } else if (stmt instanceof SelectionStatement ss) {
            return ss.getLine();
        }
        return currentFunctionLine + 1;
    }
    private String exprToString(Expr e) {
        if (e instanceof ConstantExpr) {
            return ((ConstantExpr) e).getValue();
        }
        if (e instanceof IdExpr) {
            return ((IdExpr) e).getName();
        }
        if (e instanceof BinaryExpr) {
            return ((BinaryExpr) e).getName();
        }
        if (e instanceof ArgumentExpressionList ael
                && ael.getExprlist().size() > 1) {
            Expr callee = ael.getExprlist().get(ael.getExprlist().size() - 1);
            if (callee instanceof IdExpr) {
                return ((IdExpr) callee).getName();
            }
        }
        return e.toString();
    }


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

    private int extractFunctionLine(Declarator declarator) {
        DirectDeclarator dd = declarator.getDd();
        while (dd != null) {
            if (dd.getLine() > 0) {
                return dd.getLine();
            }
            Declarator inner = dd.getDr();
            if (inner != null) {
                dd = inner.getDd();
            } else {
                dd = dd.getDd();
            }
        }
        return -1;
    }


    @Override
    public Void visit(Program program) {
        TranslationUnit translationUnit = program.getTu();
        if (translationUnit != null) {
            translationUnit.accept(this);
        }

        return null;
    }
    public Void visit(ArgumentExpressionList ael) {
        ArrayList<Expr> list = ael.getExprlist();
        if (list.size() > 1) {
            Expr last = list.get(list.size() - 1);
            if (last instanceof IdExpr id) {
                System.out.println(
                        "Line " + id.getLine() +
                                ": Expr " + id.getName()
                );
                return null;
            }
            for (int i = 1; i < list.size(); i++) {
                Expr operand = list.get(i);
                System.out.println(
                        "Line " + operand.getLine() +
                                ": Expr ,"
                );
            }
            return null;
        }
        for (Expr e : list) {
            e.accept(this);
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
        int line = binaryExpr.getLine();
        Expr left  = binaryExpr.getOperand1();
        Expr right = binaryExpr.getOperand2();
        if (line <= 0 && left != null) {
            line = left.getLine();
        }

        String op = binaryExpr.getName();
        System.out.println("Line " + line + ": Expr " + op);

        switch (op) {
            case "=": case "*=": case "/=": case "%=":
            case "+=": case "-=": case "<<=": case ">>=":
            case "&=": case "^=": case "|=":
                break;
            default:
                if (left  != null) left.accept(this);
                if (right != null) right.accept(this);
        }

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
        return null;
    }
    public Void visit(ExprList exprList) {
        return null;
    }
    public Void visit(FuncCallExpr funcCallExpr) {
        return null;
    }
    public Void visit(Identifier identifier) {
        return null;
    }
    public Void visit(IdExpr idExpr) {
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
        int line = unaryExpr.getLine();
        UnaryOperator opNode = unaryExpr.getOperator();
        if (line <= 0 && opNode != null) {
            line = opNode.getLine();
        }

        String opSymbol = opNode != null
                ? opNode.getName()
                : "UNKNOWN_OP";
        System.out.println("Line " + line + ": Expr " + opSymbol);

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

        if (compoundDepth == 1 && (stmt != null || declaration != null)) {
            stmtCount++;
            if (stmt instanceof IterationStatement it) {
                it.setStmtIndex(stmtCount);
            }
        }

        if (stmt != null) stmt.accept(this);
        if (declaration != null) declaration.accept(this);
        return null;
    }
    public Void visit(BreakState breakState) {
        return null;
    }
    public Void visit(CompoundStatement compoundStatement) {
        compoundDepth++;

        if (compoundDepth == 1) {
            System.out.println("Line " + currentFunctionLine + ": Stmt function " + currentFunctionName + " = " + compoundStatement.getBilist().size());
        }

        for (BlockItem blockItem : compoundStatement.getBilist()) {
            blockItem.accept(this);
        }

        compoundDepth--;
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
        int line     = stmt.getLine();

        if (fc == null) {
            if (cond != null) {
                System.out.println(
                        "Line " + cond.getLine() +
                                ": Expr "  + exprToString(cond)
                );
                if (line <= 0) line = cond.getLine();
            }
        } else {
            Expr forCond = fc.getE();
            if (forCond != null) {
                System.out.println(
                        "Line " + forCond.getLine() +
                                ": Expr "  + exprToString(forCond)
                );
                if (line <= 0) line = forCond.getLine();
            }
        }

        int bodyCount = 1;
        Stmt body = stmt.getStmt();
        if (body instanceof CompoundStatement cs) {
            bodyCount = cs.getBilist().size();
        }

        String kind = (fc != null) ? "for" : "while";
        System.out.println(
                "Line " + line +
                        ": Stmt " + kind + " = " + bodyCount
        );

        if (fc  != null)      fc.accept(this);
        if (body != null)     body.accept(this);

        return null;
    }
    public Void visit(SelectionStatement ss) {
        int myDepth = selectionDepth;
        Expr cond   = ss.getE();

        int headerLine = ss.getLine();
        if (headerLine <= 0) {
            if (cond != null) {
                headerLine = cond.getLine();
            } else if (ss.getS1() != null) {
                headerLine = extractFirstLineOfStmt(ss.getS1());
            }
        }

        if (cond != null && myDepth <= 1) {
            System.out.println(
                    "Line " + cond.getLine() +
                            ": Expr " + exprToString(cond)
            );
        }

        int branchCount = 1;
        Stmt thenBlock = ss.getS1();
        if (thenBlock instanceof CompoundStatement cs) {
            branchCount = cs.getBilist().size();
        }

        System.out.println(
                "Line " + headerLine +
                        ": Stmt selection = " + branchCount
        );

        selectionDepth++;
        if (thenBlock != null)    thenBlock.accept(this);
        if (ss.getS2()   != null) ss.getS2().accept(this);
        selectionDepth--;

        return null;
    }
    public Void visit(JumpStatement stmt) {
        if ("return".equals(stmt.getState())) {
            Expr expr = stmt.getE();
            if (expr != null) {
                System.out.println("Line " + expr.getLine() + ": Expr " + exprToString(expr));
            }
        }
        return null;
    }
    public Void visit(ReturnState returnState) {
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
        return null;
    }
    public Void visit(Declaration declaration) {
        InitDeclaratorList initDeclaratorList = declaration.getIdl();
        DeclarationSpecifiers declarationSpecifiers = declaration.getDss();

        if (declarationSpecifiers != null) {
            declarationSpecifiers.accept(this);
        }
        if (initDeclaratorList != null) {
            initDeclaratorList.accept(this);
        }

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
        stmtCount = 0;
        compoundDepth = 0;

        CompoundStatement body = functionDefinition.getCompoundStatement();
        currentFunctionStmtCount = body.getBilist().size();

        Declarator declarator = functionDefinition.getDeclarator();
        String name = extractFunctionName(declarator);
        int line = extractFunctionLine(declarator);

        declarator.accept(this);

        currentFunctionName = name;
        currentFunctionLine = line;

        body.accept(this);
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
        boolean hasInit = idl.getIds().stream()
                .anyMatch(initDecl -> initDecl.getInit() != null);

        if (hasInit) {
            for (int commaLine : idl.getCommaLines()) {
                System.out.println("Line " + commaLine + ": Expr ,");
            }
        }

        for (InitDeclarator decl : idl.getIds()) {
            decl.accept(this);
        }
        return null;
    }
    public Void visit(Initializer initializer) {
        Expr expr = initializer.getE();
        if (expr != null) {
            if (expr instanceof ConstantExpr || expr instanceof IdExpr) {
                System.out.println(
                        "Line " + expr.getLine()
                                + ": Expr " + exprToString(expr)
                );
            }
            expr.accept(this);
        }

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
        for (ParameterDeclaration parameterDeclaration : parameterList.getPl()) {
            parameterDeclaration.accept(this);
        }

        return null;
    }
    public Void visit(Pointer pointer) {
        return null;
    }
    public Void visit(PremetiveType premetiveType) {
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



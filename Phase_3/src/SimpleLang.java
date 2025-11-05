import main.ast.nodes.Program;
import main.grammar.SimpleLangLexer;
import main.grammar.SimpleLangParser;
import main.symbolTable.SymbolTable;
import org.antlr.v4.runtime.CharStream;
import main.visitor.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class SimpleLang {
    public static void main(String[] args) throws IOException {
        CharStream reader = CharStreams.fromFileName(args[0]);
        SimpleLangLexer simpleLangLexer = new SimpleLangLexer(reader);
        CommonTokenStream tokens = new CommonTokenStream(simpleLangLexer);
        SimpleLangParser flParser = new SimpleLangParser(tokens);
        Program program = flParser.program().programRet;


        TypeChecker my_type_checker = new TypeChecker();
        my_type_checker.visit(program);


        SymbolTable.root.hashCode();
        System.out.println();
    }
}
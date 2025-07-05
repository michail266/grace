package gr.hua.dit.compiler;

import java.io.FileReader;
import java.io.Reader;

import gr.hua.dit.compiler.ast.Program;

public class Main {

    public static void main(String[] args) {
        String filePath = args.length > 0 ? args[0] : "minimal_test.grace";
        
        System.out.println("Starting compilation of: " + filePath);
        
        try {
            Reader r = new FileReader(filePath);
            Lexer l = new Lexer(r);
            Parser p = new Parser(l);

            System.out.println("Parsing...");
            Program result = (Program) p.parse().value;
            System.out.println("Semantic analysis...");
            result.sem();
            result.compile();
            System.out.println("Compilation successful");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
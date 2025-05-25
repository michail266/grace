package gr.hua.dit.compiler;

import java.io.*;

import gr.hua.dit.compiler.ast.Program;

public class Main {

    public static void main(String[] args) {
        String filePath = "test.grace";

        try {
            // open the file (may throw FileNotFoundException)
            Reader r = new FileReader(filePath);
            Lexer l = new Lexer(r);
            Parser p = new Parser(l);

           Program result = (Program) p.parse().value;
      result.sem();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
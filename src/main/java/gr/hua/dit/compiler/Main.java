package gr.hua.dit.compiler;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        String filePath = "test.grace";

        try {
            // open the file (may throw FileNotFoundException)
            Reader r = new FileReader(filePath);
            Lexer l = new Lexer(r);
            Parser p = new Parser(l);

            // parse & execute
            Program result = (Program) p.parse().value;
            result.execute();
//          result.check();
//          result.codegen();
//          System.out.println("Value is :" + result.evaluate());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
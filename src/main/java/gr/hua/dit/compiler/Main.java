package gr.hua.dit.compiler;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            // Specify the path to test.txt
            String filePath = "test.grace";
            Reader r = new FileReader(filePath);
            Lexer l = new Lexer(r);
            Parser p = new Parser(l);

            Object result = (Integer) p.parse().value;
            System.out.println("End of test.grace" );
            
        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

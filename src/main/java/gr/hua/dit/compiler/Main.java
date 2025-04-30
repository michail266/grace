package gr.hua.dit.compiler;

import java.io.*;

import java_cup.runtime.Symbol;

public class Main {

    public static void main(String[] args) {
        Reader r = new InputStreamReader(System.in);
        Lexer l = new Lexer(r);

        try {
            Symbol token;
            while ((token = l.next_token()).sym != sym.EOF) {
                System.out.println("value of token = " + token.value);
            }
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        }
    }
}

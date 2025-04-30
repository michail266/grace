package gr.hua.dit.compiler;


import java.io.*;

public class Main {

  public static void main(String[] args) {
    Reader r = new InputStreamReader(System.in);
    Lexer l = new Lexer(r);
    Parser p = new Parser(l);
  

    try {
    int token = l.yylex();
    while (token != Lexer.YYEOF) { 
        System.out.println("Token type: "+ token + " lexeme: " + l.yytext());
        token = l.yylex();
    }

    } catch (IOException e) { 
        System.err.println(e.getMessage());
    }
  }
}
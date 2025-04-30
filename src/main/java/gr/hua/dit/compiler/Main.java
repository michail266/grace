package gr.hua.dit.compiler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {

  public static void main(String[] args) {
    Reader r = new InputStreamReader(System.in);
    Lexer l = new Lexer(r);
    //parser p = new parser(l);
    

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
package gr.hua.dit.compiler;

import java.io.*;

public class Main {

  public static void main(String[] args) {
    Reader r = new InputStreamReader(System.in);
    Lexer l = new Lexer(r);
    Parser p = new Parser(l);

    try {
      Object result = (Integer) p.parse().value;
      System.out.println("Value is:"+result);
    } catch(Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}

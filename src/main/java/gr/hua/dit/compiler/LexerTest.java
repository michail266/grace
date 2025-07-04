package gr.hua.dit.compiler;

import java.io.FileReader;
import java.io.IOException;
import java_cup.runtime.Symbol;

public class LexerTest {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java LexerTest <filename>");
            return;
        }
        
        FileReader fr = new FileReader(args[0]);
        Lexer lexer = new Lexer(fr);
        
        Symbol token;
        while ((token = lexer.next_token()).sym != Symbols.EOF) {
            System.out.println("Token: " + token.sym + " (" + getTokenName(token.sym) + ") Value: " + token.value);
        }
        System.out.println("End of file reached");
    }
    
    private static String getTokenName(int sym) {
        switch(sym) {
            case Symbols.T_fun: return "T_fun";
            case Symbols.T_id: return "T_id";
            case Symbols.T_lpar: return "T_lpar";
            case Symbols.T_rpar: return "T_rpar";
            case Symbols.T_colon: return "T_colon";
            case Symbols.T_nothing: return "T_nothing";
            case Symbols.T_var: return "T_var";
            case Symbols.T_int: return "T_int";
            case Symbols.T_char: return "T_char";
            case Symbols.T_semicolon: return "T_semicolon";
            case Symbols.T_OpCuBr: return "T_OpCuBr";
            case Symbols.T_ClCuBr: return "T_ClCuBr";
            case Symbols.T_puts: return "T_puts";
            case Symbols.T_if: return "T_if";
            case Symbols.T_then: return "T_then";
            case Symbols.T_else: return "T_else";
            case Symbols.T_assign: return "T_assign";
            case Symbols.T_coma: return "T_coma";
            case Symbols.T_minus: return "T_minus";
            case Symbols.T_Insert: return "T_Insert";
            case Symbols.T_DoQu: return "T_DoQu";
            case Symbols.T_num: return "T_num";
            default: return "UNKNOWN(" + sym + ")";
        }
    }
}

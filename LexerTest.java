import java.io.FileInputStream;
import java.io.IOException;
import gr.hua.dit.compiler.Lexer;
import gr.hua.dit.compiler.Symbols;
import java_cup.runtime.Symbol;

public class LexerTest {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java LexerTest <filename>");
            return;
        }
        
        FileInputStream fis = new FileInputStream(args[0]);
        Lexer lexer = new Lexer(fis);
        
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
            case Symbols.T_semicolon: return "T_semicolon";
            case Symbols.T_OpCuBr: return "T_OpCuBr";
            case Symbols.T_ClCuBr: return "T_ClCuBr";
            default: return "UNKNOWN";
        }
    }
}

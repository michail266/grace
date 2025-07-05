package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.types.BasicType;

public class CharLiteral extends Expr {
    private char value;
    
    public CharLiteral(String literal) {
        // Parse character literal like '\0', '\n', etc.
        if (literal.length() >= 3 && literal.startsWith("'") && literal.endsWith("'")) {
            String inner = literal.substring(1, literal.length() - 1);
            if (inner.length() == 1) {
                this.value = inner.charAt(0);
            } else if (inner.length() == 2 && inner.startsWith("\\")) {
                // Handle escape sequences
                switch (inner.charAt(1)) {
                    case '0':
                        this.value = '\0';
                        break;
                    case 'n':
                        this.value = '\n';
                        break;
                    case 't':
                        this.value = '\t';
                        break;
                    case 'r':
                        this.value = '\r';
                        break;
                    case '\'':
                        this.value = '\'';
                        break;
                    case '\"':
                        this.value = '\"';
                        break;
                    case '\\':
                        this.value = '\\';
                        break;
                    default:
                        this.value = inner.charAt(1); // Default to the character itself
                        break;
                }
            } else {
                this.value = '\0'; // Default fallback
            }
        } else {
            this.value = '\0'; // Default fallback
        }
    }
    
    public char getValue() {
        return value;
    }
    
    @Override
    public void sem(SymbolTable symbolTable) {
        type = BasicType.Char; // Character literals are of type char
    }
    
    @Override
    public String toString() {
        return "CharLiteral(" + value + ")";
    }
}

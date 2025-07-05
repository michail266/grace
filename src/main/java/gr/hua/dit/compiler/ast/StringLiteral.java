package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.types.BasicType;

public class StringLiteral extends Expr {
    private String value;
    
    public StringLiteral(String value) {
        // Remove quotes from the string
        if (value.startsWith("\"") && value.endsWith("\"")) {
            this.value = value.substring(1, value.length() - 1);
        } else {
            this.value = value;
        }
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public void sem(SymbolTable symbolTable) {
        type = BasicType.Char; // Strings are arrays of char
    }
    
    @Override
    public String toString() {
        return "StringLiteral(" + value + ")";
    }
    @Override
    public void compile(CompileContext context) {
        // Push a JVM string constant to the stack (e.g., "Hello World")
        context.addLdcInsn(value);
    }
}

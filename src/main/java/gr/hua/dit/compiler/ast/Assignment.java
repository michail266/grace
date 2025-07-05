package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.Symbol.SymbolEntry;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.errors.TypeException;

public class Assignment extends Stmt {
    private Expr lvalue;
    private Expr rvalue;
    
    public Assignment(Expr lvalue, Expr rvalue) {
        this.lvalue = lvalue;
        this.rvalue = rvalue;
    }
    
    public Expr getLValue() {
        return lvalue;
    }
    
    public Expr getRValue() {
        return rvalue;
    }
    
    @Override
    public void sem(SymbolTable tbl) throws SemanticException {
        // Semantic analysis for both sides
        lvalue.sem(tbl);
        rvalue.sem(tbl);
        
        // Check type compatibility
        if (!lvalue.getType().equals(rvalue.getType())) {
            throw new TypeException("Type mismatch in assignment: " +
                                    "Got: " + rvalue.getType() + ", " +
                                    "Expected: " + lvalue.getType());
        }
    }
    
    @Override
    public String toString() {
        return "Assignment(" + lvalue + " <- " + rvalue + ")";
    }
}

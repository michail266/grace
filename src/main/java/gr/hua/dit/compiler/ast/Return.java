package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.SymbolTable;

public class Return extends Stmt {
    private Expr expr;
    
    public Return(Expr expr) {
        this.expr = expr;
    }
    
    public Return() {
        this.expr = null; // return without value
    }
    
    public Expr getExpr() { return expr; }
    
    public String toString() {
        if (expr != null) {
            return "Return(" + expr + ")";
        } else {
            return "Return()";
        }
    }
    
    public void sem(SymbolTable tbl) throws SemanticException {
        if (expr != null) {
            expr.sem(tbl);
        }
        // TODO: Type checking against function return type would go here
    }
}

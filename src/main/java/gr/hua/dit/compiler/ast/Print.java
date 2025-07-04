package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.SymbolTable;

public class Print extends Stmt {
  private Expr e;
  public Print(Expr e) { this.e = e; }

  public String toString() { return "Print(" + e + ")"; }
  
  public void sem(SymbolTable tbl) throws SemanticException {
    e.sem(tbl);
  }
}

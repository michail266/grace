package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.types.*;


public class For extends Stmt {

  private Expr e;
  private Stmt s;

  public For(Expr e, Stmt s) {
    this.e = e;
    this.s = s;
  }

  public String toString() { return "For(" +e + "," + s + ")"; }


  public void sem(SymbolTable tbl) throws SemanticException {
    e.typeCheck(tbl,BasicType.Int);
    s.sem(tbl);
  }
}

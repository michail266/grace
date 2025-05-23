package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.types.*;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.*;

public class If extends Stmt {
  private Expr cond;
  private Stmt then_stmt;
  private Stmt else_stmt;

  public If(Expr c, Stmt s1, Stmt s2) {
    this.cond = c;
    this.then_stmt = s1;
    this.else_stmt = s2;
  }

  public If(Expr c, Stmt s1) {
    this(c,s1,null);
  }

  public void sem(SymbolTable tbl) throws SemanticException {
    cond.typeCheck(tbl, BasicType.Bool);
    then_stmt.sem(tbl);
    if (else_stmt != null)
      else_stmt.sem(tbl);
  }
}

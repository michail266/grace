package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.errors.TypeException;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.types.*;

public abstract class Expr extends ASTNode {

  protected Type type;

  // check if expression is well-typed
  public void typeCheck(SymbolTable tbl, Type t) throws SemanticException {
    // analysis will populate the inferred type of Expr
    sem(tbl);
    if (getType().equals(t)) {
      throw new TypeException("Type mismatch: " +
                              "Got: " + getType() + ", " +
                              "Expected: " + t);
    }
  }

  public Type getType() { return type; }
}

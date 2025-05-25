package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.errors.SemanticException;

import gr.hua.dit.compiler.types.*;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.*;

public class BinOp extends Expr {

  public enum Operator {
    Plus,
    Minus,
    Times,
    Div
  };

  private Operator op;
  private Expr l, r;

  public BinOp(Operator o, Expr l, Expr r) {
    this.op = o;
    this.l = l;
    this.r = r;
  }

  public String toString() { return op + "(" + l + "," + r + ")"; }

  public void sem(SymbolTable tbl) throws SemanticException {
    l.typeCheck(tbl, BasicType.Int);
    r.typeCheck(tbl, BasicType.Int);
    type = BasicType.Int;
  }

}

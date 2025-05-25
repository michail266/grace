package gr.hua.dit.compiler;

import gr.hua.dit.compiler.ast.Expr;

public class NumExpr extends Expr {
  public Integer num;

  public NumExpr(Integer num) {
    this.num = num;
  }

  public String toString() {
    return "" + num;
  }

  public Integer evaluate() {
    return num;
  }
}

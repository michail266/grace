package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.types.*;

public class NumConst extends Expr {
  private Integer value;
  public NumConst(Integer value) {
    this.value = value;
  }

  public String toString() { return "NumConst(" + value.toString() + ")"; }

  public void sem() {
    type = BasicType.Int;
  }
}

package gr.hua.dit.compiler;

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

package gr.hua.dit.compiler;

public class TimesExpr extends Expr {

  private Expr e1;
  private Expr e2;

  public TimesExpr(Expr e1, Expr e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public String toString() {
    return "(" + e1.toString() + " * " + e2.toString() + ")";
  }

  public Integer evaluate() {
    return e1.evaluate() * e2.evaluate();
  }
}

package gr.hua.dit.compiler;

public class IfStatement extends Statement {

  private Expr c;
  private Statement t;
  private Statement e;

  public IfStatement(Expr c, Statement t, Statement e) {
    this.c = c;
    this.t = t;
    this.e = e;
  }

  public void execute() {
    if (c.evaluate() > 0) {
      t.execute();
    } else {
      e.execute();
    }
  }
}

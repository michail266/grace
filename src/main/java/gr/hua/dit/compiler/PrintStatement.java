package gr.hua.dit.compiler;

public class PrintStatement extends Statement {

  private Expr e;
  public PrintStatement(Expr e) {
    this.e = e;
  }

  public void execute() {
    System.out.println(e.evaluate());
  }
}

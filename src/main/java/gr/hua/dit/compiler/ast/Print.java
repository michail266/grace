package gr.hua.dit.compiler.ast;

public class Print extends Stmt {
  private Expr e;
  public Print(Expr e) { this.e = e; }

  public String toString() { return "Print(" + e + ")"; }
}

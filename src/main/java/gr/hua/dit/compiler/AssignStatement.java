package gr.hua.dit.compiler;

import gr.hua.dit.compiler.ast.Expr;

public class AssignStatement extends Statement {
  private String variable;
  private Expr e;

  public AssignStatement(String variable, Expr e) {
    this.variable = variable;
    this.e = e;
  }

  public void execute() {
    Memory.mem.put(variable, e.evaluate());
  }
}

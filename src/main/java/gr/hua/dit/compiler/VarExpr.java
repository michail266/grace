package gr.hua.dit.compiler;

public class VarExpr extends Expr {
  private String variable_name;
  public VarExpr(String variable_name) {
    this.variable_name = variable_name;
  }

  public String toString() {
    return variable_name;
  }

  public Integer evaluate() {
    return Memory.mem.getOrDefault(variable_name, 0);
  }
}

package gr.hua.dit.compiler;

import java.util.List;

public class Program {

  private List<Statement> stmts;

  public Program(List<Statement> stmts) {
    this.stmts = stmts;
  }

  public void execute() {
    for (Statement stmt : stmts) {
      stmt.execute();
    }
  }
}

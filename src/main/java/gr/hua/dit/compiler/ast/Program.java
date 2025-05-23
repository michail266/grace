package gr.hua.dit.compiler.ast;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.SymbolTable;

public class Program  extends ASTNode {

  private List<Stmt> stmts;
  private List<Decl> decls;

  public Program(List<Stmt> stmts) {
    this(Collections.emptyList(), stmts);
  }

  public Program(List<Decl> decls, List<Stmt> stmts) {
    this.decls = decls;
    this.stmts = stmts;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    StringJoiner sj = new StringJoiner(",", "Program(", ")");
    for (Decl d : decls) sj.add(d.toString());
    for (Stmt s : stmts) sj.add(s.toString());
    return sj.toString();
  }

  public void sem(SymbolTable tbl) throws SemanticException {
    for (Decl d : decls) d.sem(tbl);
    for (Stmt s : stmts) s.sem(tbl);
  }

  public void sem() throws SemanticException {
    this.sem(new SymbolTable());
  }
}

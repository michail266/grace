package gr.hua.dit.compiler.ast;

import java.util.List;
import java.util.StringJoiner;

import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.*;

public class Block extends Stmt {
  private List<Stmt> stmts;
  private List<Decl> decls;

  public Block(List<Decl> decls, List<Stmt> stmts) {
    this.stmts = stmts;
    this.decls = decls;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    StringJoiner sj = new StringJoiner(",", "Block(", ")");
    for (Stmt s : stmts) sj.add(s.toString());
    return sj.toString();
  }

  public void sem(SymbolTable tbl) throws SemanticException {
    tbl.openScope();
    for (Decl d : decls) d.sem(tbl);
    for (Stmt s : stmts) s.sem(tbl);
    tbl.closeScope();
  }
}

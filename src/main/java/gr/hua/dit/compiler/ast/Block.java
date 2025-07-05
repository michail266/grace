package gr.hua.dit.compiler.ast;

import java.util.List;
import java.util.StringJoiner;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;

public class Block extends Stmt {
  private List<Stmt> stmts;
  private List<Decl> decls;

  public Block(List<Decl> decls, List<Stmt> stmts) {
    this.decls = decls;
    this.stmts = stmts;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    StringJoiner sj = new StringJoiner(",", "Block(", ")");
    for (Stmt s : stmts) sj.add(s.toString());
    return sj.toString();
  }

  public void sem(SymbolTable tbl) throws SemanticException {
    // Always open new scope for blocks that have declarations (function definitions)
    boolean openedScope = false;
    if (!decls.isEmpty()) {
      tbl.openScope();
      openedScope = true;
    }
    
    try {
      // Process declarations first (function parameters)
      for (Decl d : decls) {
        d.sem(tbl);
      }
      
      // Then process statements (function body)
      for (Stmt s : stmts) {
        s.sem(tbl);
      }
    } finally {
      // Always close scope if we opened one
      if (openedScope) {
        tbl.closeScope();
      }
    }
  }

  public List<Decl> getDecls() {
    return decls;
  }

  public List<Stmt> getStmts() {
    return stmts;
  }
    public void compile(CompileContext context) {
    for (Stmt s : stmts) {
      s.compile(context);
    }
  }
}

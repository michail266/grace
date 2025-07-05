package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.SymbolTable;

public class FuncCallStmt extends Stmt {
  private FuncCall funcCall;
  
  public FuncCallStmt(FuncCall funcCall) { 
    this.funcCall = funcCall; 
  }

  public String toString() { 
    return "FuncCallStmt(" + funcCall + ")"; 
  }
  
  public void sem(SymbolTable tbl) throws SemanticException {
    funcCall.sem(tbl);
  }
  
  public void compile(CompileContext context) {
    funcCall.compile(context);
  }
}

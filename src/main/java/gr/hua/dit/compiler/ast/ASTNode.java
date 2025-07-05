package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;

public abstract class ASTNode {


  // do semantic analysis in this node
  public void sem(SymbolTable tbl) throws SemanticException { }


  public void compile(CompileContext context) { }

}
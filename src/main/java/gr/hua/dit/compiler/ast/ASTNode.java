package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.SymbolTable;

public abstract class ASTNode {


  // do semantic analysis in this node
  public void sem(SymbolTable tbl) throws SemanticException { }

}

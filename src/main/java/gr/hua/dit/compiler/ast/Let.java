package gr.hua.dit.compiler.ast;


import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.errors.TypeException;
import gr.hua.dit.compiler.Symbol.*;

public class Let extends Stmt {
  private String id;
  private Expr e;

  public Let(String id, Expr e) {
    this.id = id;
    this.e = e;
  }

  public String toString() {
    return "Let("+id+","+e+")";
  }

  public void sem(SymbolTable tbl) throws SemanticException{
    // get type of id;
    SymbolEntry entry = tbl.lookup(id);
    if (entry != null) {
      e.typeCheck(tbl,entry.getType());
    } else {
      throw new TypeException("Variable '" + id + "' not declared");
    }
  }
}

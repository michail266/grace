
package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.errors.TypeException;
import gr.hua.dit.compiler.Symbol.*;

public class Id extends Expr {
  private String name;
  public Id(String name) {
    this.name = name;
  }

  public String toString() { return "Id(" + name + ")"; }

  public void sem(SymbolTable tbl) throws SemanticException {
    SymbolEntry e = tbl.lookup(name);
    if (e != null) {
      type = e.getType();
    } else {
      throw new TypeException("Variable '" + name + "' not declared");
    }
  }
}

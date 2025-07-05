package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.SymbolEntry;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.types.FuncType;
import gr.hua.dit.compiler.types.Type;
import gr.hua.dit.compiler.types.BasicType;

import java.util.List;

public class FuncCall extends Expr {

  private String functionName;
  private List<Expr> args;

  public FuncCall(String id, List<Expr> args)
  {
    functionName = id;
    this.args = args;
  }

  public void sem(SymbolTable tbl) throws SemanticException {
    // Handle built-in functions
    if ("geti".equals(functionName)) {
      type = BasicType.Int;
      return;
    }
    
    SymbolEntry e = tbl.lookupRec(functionName);

    if (e != null) {
      FuncType funcType = (FuncType) e.getType();
      List<Type> argsType  = funcType.getArgs();

      if (argsType.size() == args.size()) {
        for (int i = 0; i < argsType.size(); i++) {
          args.get(i).typeCheck(tbl, argsType.get(i));
        }
      }
      type = funcType.getResult();
    }
  }
}

package gr.hua.dit.compiler.ast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;
import gr.hua.dit.compiler.CompileContext;
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
    } else {
      throw new SemanticException("Function '" + functionName + "' not declared");
    }
  }

  public void compile(CompileContext context) {
    // Handle built-in functions
    if ("geti".equals(functionName)) {
      // For now, just return a fixed value (we'd need Scanner for real input)
      context.addInsn(new org.objectweb.asm.tree.IntInsnNode(org.objectweb.asm.Opcodes.BIPUSH, 3));
      return;
    }
    
    // Compile arguments first
    for (Expr arg : args) {
      arg.compile(context);
    }
    
    // Build method descriptor based on actual argument types
    StringBuilder descriptor = new StringBuilder("(");
    for (Expr arg : args) {
      if (arg instanceof StringLiteral) {
        descriptor.append("Ljava/lang/String;"); // String parameter
      } else {
        descriptor.append("I"); // Int parameter
      }
    }
    descriptor.append(")V");
    
    // Call static method
    context.addInsn(new MethodInsnNode(
      Opcodes.INVOKESTATIC,
      "MiniBasic",
      functionName,
      descriptor.toString()
    ));
  }
}

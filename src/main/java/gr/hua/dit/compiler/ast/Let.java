package gr.hua.dit.compiler.ast;


import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolEntry;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.errors.TypeException;

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
    SymbolEntry entry = tbl.lookupRec(id);
    if (entry != null) {
      e.typeCheck(tbl,entry.getType());
    } else {
      throw new TypeException("Variable '" + id + "' not declared");
    }
  }
  public void compile(CompileContext context) {
    // Get the variable index (consistent with Id.java)
    int varIndex = Math.abs(this.id.hashCode()) % 100;
    
    // Get global array reference
    context.addInsn(new FieldInsnNode(
      Opcodes.GETSTATIC, 
      "MiniBasic", 
      "theVars", 
      "[I"
    ));
    
    // Push array index
    context.addInsn(new org.objectweb.asm.tree.IntInsnNode(Opcodes.BIPUSH, varIndex));
    
    // Compile the right-hand side expression
    e.compile(context);
    
    // Store in array
    context.addInsn(new InsnNode(Opcodes.IASTORE));
  }
}

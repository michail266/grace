package gr.hua.dit.compiler.ast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolEntry;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.errors.TypeException;

public class Id extends Expr {
  private String name;
  public Id(String name) {
    this.name = name;
  }

  public String toString() { return "Id(" + name + ")"; }
  
  public String getName() { return name; }

  public void sem(SymbolTable tbl) throws SemanticException {
    SymbolEntry e = tbl.lookupRec(name);
    if (e != null) {
      type = e.getType();
    } else {
      throw new TypeException("Variable '" + name + "' not declared");
    }
  }

  public String getId() {
    return name;
  }
  public void compile(CompileContext context) {
    // Get the variable index (consistent with Assignment.java)
    int varIndex = Math.abs(this.name.hashCode()) % 100;
    
    // Get global array reference
    context.addInsn(new FieldInsnNode(
      Opcodes.GETSTATIC, 
      "MiniBasic", 
      "theVars", 
      "[I"
    ));
    
    // Push array index  
    context.addInsn(new org.objectweb.asm.tree.IntInsnNode(Opcodes.BIPUSH, varIndex));
    
    // Load from array
    context.addInsn(new InsnNode(Opcodes.IALOAD));
  }
}

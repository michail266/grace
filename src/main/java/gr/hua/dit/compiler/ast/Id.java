package gr.hua.dit.compiler.ast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.VarInsnNode;

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

  public void sem(SymbolTable tbl) throws SemanticException {
    SymbolEntry e = tbl.lookup(name);
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
//    context.getMainNode().instructions.add(
//      new VarInsnNode(Opcodes.ILOAD, this.name.charAt(0) - 'a'));
      context.addInsn(new FieldInsnNode(Opcodes.GETSTATIC, context.getClassNode().name, "theVars", "[I")
      );
      context.addInsn(new VarInsnNode(Opcodes.BIPUSH, this.name.charAt(0) - 'a'));
      context.addInsn(new InsnNode(Opcodes.IALOAD));
  }
}

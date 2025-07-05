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
    public void compile(CompileContext context) {
      context.addInsn(
        new FieldInsnNode(Opcodes.GETSTATIC, context.getClassNode().name, "theVars", "[I")
      );
      context.addInsn(new VarInsnNode(Opcodes.BIPUSH, this.id.charAt(0) - 'a'));
      e.compile(context);
      context.addInsn(new InsnNode(Opcodes.IASTORE));

//      context.getMainNode().instructions.add(
//        new VarInsnNode(Opcodes.ISTORE, this.id.charAt(0) - 'a'));
  }
}

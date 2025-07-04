package gr.hua.dit.compiler.ast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.types.BasicType;

public class NumConst extends Expr {
  private Integer value;
  public NumConst(Integer value) {
    this.value = value;
  }

  public String toString() { return "NumConst(" + value.toString() + ")"; }

  public void sem(SymbolTable tbl) {
    type = BasicType.Int;
  }
    public void compile(CompileContext context) {
    if (this.value < 6 && this.value >= -1 )
    {
      switch (this.value) {
        case -1: context.addInsn(new InsnNode(Opcodes.ICONST_M1)); break;
        case 0: context.addInsn(new InsnNode(Opcodes.ICONST_0)); break;
        case 1: context.addInsn(new InsnNode(Opcodes.ICONST_1)); break;
        case 2: context.addInsn(new InsnNode(Opcodes.ICONST_2)); break;
        case 3: context.addInsn(new InsnNode(Opcodes.ICONST_3)); break;
        case 4: context.addInsn(new InsnNode(Opcodes.ICONST_4)); break;
        case 5: context.addInsn(new InsnNode(Opcodes.ICONST_5)); break;
      }

    } else if (this.value < 128 && this.value >= -128) {
      context.addInsn(new VarInsnNode(Opcodes.BIPUSH, this.value));
    } else {
      context.addInsn(new VarInsnNode(Opcodes.SIPUSH, this.value));
    }

  }
}

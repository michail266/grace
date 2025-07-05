package gr.hua.dit.compiler.ast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.VarInsnNode;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.types.BasicType;


public class For extends Stmt {

  private Expr e;
  private Stmt s;

  public For(Expr e, Stmt s) {
    this.e = e;
    this.s = s;
  }

  public String toString() { return "For(" +e + "," + s + ")"; }


  public void sem(SymbolTable tbl) throws SemanticException {
    e.typeCheck(tbl,BasicType.Int);
    s.sem(tbl);
  }
    public void compile(CompileContext context) {
    int t = context.newTemp();
    LabelNode l1 = new LabelNode();
    LabelNode l2 = new LabelNode();

    e.compile(context);
    context.addInsn(new VarInsnNode(Opcodes.ISTORE, t));
    context.addInsn(l1);
    context.addInsn(new VarInsnNode(Opcodes.ILOAD, t));
    context.addInsn(new InsnNode(Opcodes.ICONST_0));
    context.addInsn(new JumpInsnNode(Opcodes.IF_ICMPEQ, l2));
    s.compile(context);
    context.addInsn(new VarInsnNode(Opcodes.ILOAD, t));
    context.addInsn(new InsnNode(Opcodes.ICONST_M1));
    context.addInsn(new InsnNode(Opcodes.IADD));
    context.addInsn(new VarInsnNode(Opcodes.ISTORE, t));
    context.addInsn(new JumpInsnNode(Opcodes.GOTO, l1));
    context.addInsn(l2);
    context.addInsn(new InsnNode(Opcodes.NOP));
  }
}

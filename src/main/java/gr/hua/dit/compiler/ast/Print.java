package gr.hua.dit.compiler.ast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import gr.hua.dit.compiler.CompileContext;

public class Print extends Stmt {
  private Expr e;
  public Print(Expr e) { this.e = e; }

  public String toString() { return "Print(" + e + ")"; }

    public void compile(CompileContext context) {
    context.addInsn(new FieldInsnNode(Opcodes.GETSTATIC,
      "java/lang/System", "out", "Ljava/io/PrintStream;"
    ));
    e.compile(context);
    context.addInsn(
      new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
        "java/io/PrintStream", "println", "(I)V"));
  }
  
}

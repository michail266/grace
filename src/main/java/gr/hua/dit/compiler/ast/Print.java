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
    // Get System.out
    context.addInsn(new FieldInsnNode(
        Opcodes.GETSTATIC,
        "java/lang/System",
        "out",
        "Ljava/io/PrintStream;"
    ));

    // Compile the expression (either int or string)
    e.compile(context);

    // Decide println descriptor based on Expr type
    String descriptor;
    if (e instanceof StringLiteral) {
        descriptor = "(Ljava/lang/String;)V";
    } else {
        descriptor = "(I)V";  // Default to int
    }

    context.addInsn(new MethodInsnNode(
        Opcodes.INVOKEVIRTUAL,
        "java/io/PrintStream",
        "println",
        descriptor
    ));
}
}
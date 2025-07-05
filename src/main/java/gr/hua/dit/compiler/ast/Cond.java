package gr.hua.dit.compiler.ast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.errors.TypeException;
import gr.hua.dit.compiler.types.Type;

public class Cond extends ASTNode {

  private Expr e;

  public Cond(Expr e) { this.e = e; }

  public void typeCheck(SymbolTable tbl, Type t) throws SemanticException {
    // analysis sets the type of Expr
    sem(tbl);
    e.sem(tbl);
    if (e.getType().equals(t)) {
      throw new TypeException("Type mismatch: " +
        "Got: " + e.getType() + ", " +
        "Expected: " + t);
    }
  }

  public void compile(CompileContext context,
                      LabelNode trueLabel,
                      LabelNode falseLabel) {

    e.compile(context);
    context.addInsn(new InsnNode(Opcodes.ICONST_0));
    context.addInsn(new JumpInsnNode(Opcodes.IF_ICMPNE, trueLabel));
    context.addInsn(new JumpInsnNode(Opcodes.GOTO, falseLabel));
  }
}
package gr.hua.dit.compiler.ast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnNode;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.types.BasicType;

public class BinOp extends Expr {

  public enum Operator {
    Plus,
    Minus,
    Times,
    Div,
    Mod,
    Eq,
    NotEq,
    Less,
    Greater,
    LessEq,
    GreaterEq
  };

  private Operator op;
  private Expr l, r;

  public BinOp(Operator o, Expr l, Expr r) {
    this.op = o;
    this.l = l;
    this.r = r;
  }

  public String toString() { return op + "(" + l + "," + r + ")"; }

  public void sem(SymbolTable tbl) throws SemanticException {
    // First, perform semantic analysis on operands
    l.sem(tbl);
    r.sem(tbl);
    
    // Then perform type checking
    l.typeCheck(tbl, BasicType.Int);
    r.typeCheck(tbl, BasicType.Int);
    
    // All operators return INT (Grace treats non-zero as true)
    type = BasicType.Int;
  }

    public void compile(CompileContext context) {
    l.compile(context);
    r.compile(context);
    switch (this.op) {
      case Plus:
        context.addInsn(new InsnNode(Opcodes.IADD));
        break;
      case Minus:
        context.addInsn(new InsnNode(Opcodes.ISUB));
        break;
      case Times:
        context.addInsn(new InsnNode(Opcodes.IMUL));
        break;
      case Div:
        context.addInsn(new InsnNode(Opcodes.IDIV));
        break;
      case Mod:
        context.addInsn(new InsnNode(Opcodes.IREM));
        break;
      case Eq:
        // TODO: Add equality comparison bytecode
        break;
      case NotEq:
        // TODO: Add not equal comparison bytecode
        break;
      case Less:
        // TODO: Add less than comparison bytecode
        break;
      case LessEq:
        // TODO: Add less than or equal comparison bytecode
        break;
      case Greater:
        // TODO: Add greater than comparison bytecode
        break;
      case GreaterEq:
        // TODO: Add greater than or equal comparison bytecode
        break;
    }
  }

}

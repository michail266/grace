package gr.hua.dit.compiler.ast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.types.BasicType;

public class If extends Stmt {
  private Expr cond;
  private Stmt then_stmt;
  private Stmt else_stmt;

  public If(Expr c, Stmt s1, Stmt s2) {
    this.cond = c;
    this.then_stmt = s1;
    this.else_stmt = s2;
  }

  public If(Expr c, Stmt s1) {
    this(c,s1,null);
  }

  public void sem(SymbolTable tbl) throws SemanticException {
    cond.typeCheck(tbl, BasicType.Int); //Treats non zero as true
    then_stmt.sem(tbl);
    if (else_stmt != null)
      else_stmt.sem(tbl);
  }

  public void compile(CompileContext context) {
    // Compile condition
    cond.compile(context);
    
    // Create labels for control flow
    LabelNode elseLabel = new LabelNode();
    LabelNode endLabel = new LabelNode();
    
    // If condition is 0 (false), jump to else
    context.addInsn(new JumpInsnNode(Opcodes.IFEQ, elseLabel));
    
    // Compile then statement
    then_stmt.compile(context);
    
    // Jump to end
    context.addInsn(new JumpInsnNode(Opcodes.GOTO, endLabel));
    
    // Else label
    context.addInsn(elseLabel);
    
    // Compile else statement if it exists
    if (else_stmt != null) {
      else_stmt.compile(context);
    }
    
    // End label
    context.addInsn(endLabel);
  }
}
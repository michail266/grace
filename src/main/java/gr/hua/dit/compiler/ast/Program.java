package gr.hua.dit.compiler.ast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.TraceClassVisitor;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;

public class Program  extends ASTNode {

  private List<Stmt> stmts;
  private List<Decl> decls;

  public Program(List<Stmt> stmts) {
    this(Collections.emptyList(), stmts);
  }

  public Program(List<Decl> decls, List<Stmt> stmts) {
    this.decls = decls;
    this.stmts = stmts;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    StringJoiner sj = new StringJoiner(",", "Program(", ")");
    for (Decl d : decls) sj.add(d.toString());
    for (Stmt s : stmts) sj.add(s.toString());
    return sj.toString();
  }

  public void sem(SymbolTable tbl) throws SemanticException {
    for (Decl d : decls) d.sem(tbl);
    for (Stmt s : stmts) s.sem(tbl);
  }

  public void sem() throws SemanticException {
    this.sem(new SymbolTable());
  }
  public void compile() throws IOException {
    CompileContext context = new CompileContext();
    ClassNode cn = new ClassNode();
    cn.access = Opcodes.ACC_PUBLIC;
    cn.version = Opcodes.V1_5;
    cn.name = "MiniBasic";
    cn.sourceFile = "MiniBasic.in";
    cn.superName = "java/lang/Object";
    cn.fields.add(new FieldNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
              "theVars", "[I" , null, null));

    MethodNode mn;


    context.setClassNode(cn);

    mn = new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);

    context.setMainNode(mn);

    for (Stmt s : stmts) {
      s.compile(context);
    }
    context.addInsn(new InsnNode(Opcodes.RETURN));
    mn.maxLocals = context.getNumberOfTemps();
    mn.maxStack = 128;
    cn.methods.add(mn);

    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
    TraceClassVisitor cv = new TraceClassVisitor(cw, new PrintWriter(System.out));
    cn.accept(cv);

    byte code[] = cw.toByteArray();

    FileOutputStream fos = new FileOutputStream("MiniBasic.class");
    fos.write(code);
    fos.close();
  }
}

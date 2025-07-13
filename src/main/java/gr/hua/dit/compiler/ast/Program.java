package gr.hua.dit.compiler.ast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.MethodInsnNode;
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

    context.setClassNode(cn);

    // Create static initializer to initialize theVars array
    MethodNode clinit = new MethodNode(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
    context.setMainNode(clinit);
    
    // Initialize theVars array with size 100
    context.addInsn(new IntInsnNode(Opcodes.BIPUSH, 100));
    context.addInsn(new IntInsnNode(Opcodes.NEWARRAY, Opcodes.T_INT));
    context.addInsn(new FieldInsnNode(Opcodes.PUTSTATIC, "MiniBasic", "theVars", "[I"));
    context.addInsn(new InsnNode(Opcodes.RETURN));
    
    clinit.maxLocals = 0;
    clinit.maxStack = 1;
    cn.methods.add(clinit);

    // Handle the special Grace syntax pattern where functions are defined with headers followed by blocks
    // Look for the main function and check if it has nested function headers
    boolean handledMainFunction = false;
    
    for (Stmt s : stmts) {
      if (s instanceof FuncDef) {
        FuncDef funcDef = (FuncDef) s;
        if ("main".equals(funcDef.getName()) && !handledMainFunction) {
          // Check if this is the Hanoi-style main function with nested headers
          List<FuncDef> processedFunctions = processFunctionWithNestedHeaders(funcDef);
          for (FuncDef processedFunc : processedFunctions) {
            // For all functions in the special case, skip nested functions since we're handling them separately
            processedFunc.compile(context, true);
          }
          handledMainFunction = true;
        } else if (!"main".equals(funcDef.getName())) {
          funcDef.compile(context);
        }
      }
    }
    
    // If we didn't handle main function through special processing, compile it normally
    if (!handledMainFunction) {
      for (Stmt s : stmts) {
        if (s instanceof FuncDef) {
          s.compile(context);
        }
      }
    }

    // Then create the main method that calls the Grace main function
    MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
    context.setMainNode(mn);
    
    // Call the Grace main function
    context.addInsn(new MethodInsnNode(Opcodes.INVOKESTATIC, "MiniBasic", "main", "()V"));
    context.addInsn(new InsnNode(Opcodes.RETURN));
    
    mn.maxLocals = 1;
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
  
  private List<FuncDef> processFunctionWithNestedHeaders(FuncDef mainFunc) {
    List<FuncDef> result = new ArrayList<>();
    
    // Extract all nested functions recursively
    List<FuncDef> allFunctions = new ArrayList<>();
    extractAllFunctions(mainFunc, allFunctions);
    

    result.add(mainFunc);
    return result;
  }
  
  private void extractAllFunctions(FuncDef func, List<FuncDef> allFunctions) {
    allFunctions.add(func);
    
    for (Stmt stmt : func.getBody().getStmts()) {
      if (stmt instanceof FuncDef) {
        extractAllFunctions((FuncDef) stmt, allFunctions);
      }
    }
  }
}

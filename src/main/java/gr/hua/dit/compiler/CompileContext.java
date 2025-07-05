package gr.hua.dit.compiler;

import gr.hua.dit.compiler.Symbol.SymbolTable;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;


import java.util.HashMap;
import java.util.Map;

public class CompileContext {

  private SymbolTable symbolTable;

  private ClassNode classNode;
  private MethodNode mainNode;

  private Map<String, Def> registry = new HashMap<String, Def>();

  public SymbolTable getSymbolTable() {
    return symbolTable;
  }

  public void setSymbolTable(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  public Def getGlobal(String name) {
    return registry.get(name);
  }

  public void setGlobal(String name) {
    registry.put(name, null);
  }

  public ClassNode getClassNode() {
    return classNode;
  }

  public void setClassNode(ClassNode classNode) {
    this.classNode = classNode;
  }

  public MethodNode getMainNode() {
    return mainNode;
  }

  public void setMainNode(MethodNode mainNode) {
    this.mainNode = mainNode;
  }

  public void addInsn(AbstractInsnNode insn) {
    this.getMainNode().instructions.add(insn);
  }

  private static int nextTemp = 0;
  public int newTemp() {
    nextTemp++;
    return nextTemp - 1;
  }
  public void addLdcInsn(Object cst) {
    if (mainNode == null) {
        throw new IllegalStateException("Main method is not set");
    }
    mainNode.instructions.add(new LdcInsnNode(cst));
}


  public int getNumberOfTemps() {
    return nextTemp + 1;
  }
}
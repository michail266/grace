package gr.hua.dit.compiler.ast;

import java.util.List;

import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.types.FuncType;
import gr.hua.dit.compiler.types.Type;

public class FuncDef extends Stmt {
    private String name;
    private List<Decl> params;
    private Type returnType;
    private Block body;
    
    public FuncDef(String name, List<Decl> params, Type returnType, Block body) {
        this.name = name;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }
    
    public String getName() { return name; }
    public List<Decl> getParams() { return params; }
    public Type getReturnType() { return returnType; }
    public Block getBody() { return body; }
    
    public String toString() {
        return "FuncDef(" + name + ", " + params + ", " + returnType + ", " + body + ")";
    }
    
    public void sem(SymbolTable tbl) throws SemanticException {
        // Create function type from parameters and return type
        List<Type> paramTypes = new java.util.ArrayList<>();
        for (Decl param : params) {
            paramTypes.add(param.getType());
        }
        FuncType funcType = new FuncType(paramTypes, returnType);
        
        // Add function to symbol table
        tbl.addEntry(name, funcType);
        
        // Process function body in new scope
        tbl.openScope();
        try {
            // Add parameters to function scope
            for (Decl param : params) {
                param.sem(tbl);
            }
            
            // Process function body
            body.sem(tbl);
        } finally {
            tbl.closeScope();
        }
    }
    
    public void compile(CompileContext context) {
        compile(context, false);
    }
    
    public void compile(CompileContext context, boolean skipNestedFunctions) {
        // Build method descriptor based on parameters
        StringBuilder descriptor = new StringBuilder("(");
        for (Decl param : params) {
            // Check parameter type
            if (param.getType() instanceof gr.hua.dit.compiler.types.ArrayType) {
                descriptor.append("Ljava/lang/String;"); // char[] becomes String
            } else {
                descriptor.append("I"); // int parameter
            }
        }
        descriptor.append(")V");
        
        // Create a method for each function
        org.objectweb.asm.tree.MethodNode mn = new org.objectweb.asm.tree.MethodNode(
            org.objectweb.asm.Opcodes.ACC_PUBLIC + org.objectweb.asm.Opcodes.ACC_STATIC,
            name,
            descriptor.toString(),
            null,
            null
        );
        
        // Save current method and set new one
        org.objectweb.asm.tree.MethodNode oldMethod = context.getMainNode();
        context.setMainNode(mn);
        
        // Compile the function body
        if (skipNestedFunctions) {
            // Compile only the statements that are not FuncDef
            for (Stmt stmt : body.getStmts()) {
                if (!(stmt instanceof FuncDef)) {
                    stmt.compile(context);
                }
            }
        } else {
            body.compile(context);
        }
        
        // Add return instruction
        context.addInsn(new org.objectweb.asm.tree.InsnNode(org.objectweb.asm.Opcodes.RETURN));
        
        // Set method limits
        mn.maxLocals = context.getNumberOfTemps() + params.size();
        mn.maxStack = 128;
        
        // Add method to class
        context.getClassNode().methods.add(mn);
        
        // Restore previous method
        context.setMainNode(oldMethod);
    }
}

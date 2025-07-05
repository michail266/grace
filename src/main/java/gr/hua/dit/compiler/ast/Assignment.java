package gr.hua.dit.compiler.ast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import gr.hua.dit.compiler.CompileContext;
import gr.hua.dit.compiler.Symbol.SymbolEntry;
import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.errors.TypeException;

public class Assignment extends Stmt {
    private Expr lvalue;
    private Expr rvalue;
    
    public Assignment(Expr lvalue, Expr rvalue) {
        this.lvalue = lvalue;
        this.rvalue = rvalue;
    }
    
    public Expr getLValue() {
        return lvalue;
    }
    
    public Expr getRValue() {
        return rvalue;
    }
    
    @Override
    public void sem(SymbolTable tbl) throws SemanticException {
        // Semantic analysis for both sides
        lvalue.sem(tbl);
        rvalue.sem(tbl);
        
        // Check type compatibility
        if (!lvalue.getType().equals(rvalue.getType())) {
            throw new TypeException("Type mismatch in assignment: " +
                                    "Got: " + rvalue.getType() + ", " +
                                    "Expected: " + lvalue.getType());
        }
    }
    
    public void compile(CompileContext context) {
        // For now, handle simple variable assignment to global array
        if (lvalue instanceof Id) {
            Id id = (Id) lvalue;
            
            // Get the variable index (simple hash for now)
            int varIndex = Math.abs(id.getName().hashCode()) % 100;
            
            // Get global array reference
            context.addInsn(new FieldInsnNode(
                Opcodes.GETSTATIC,
                "MiniBasic",
                "theVars",
                "[I"
            ));
            
            // Push array index
            context.addInsn(new IntInsnNode(Opcodes.BIPUSH, varIndex));
            
            // Compile the right-hand side expression
            rvalue.compile(context);
            
            // Store in array
            context.addInsn(new org.objectweb.asm.tree.InsnNode(Opcodes.IASTORE));
        }
    }
    
    @Override
    public String toString() {
        return "Assignment(" + lvalue + " <- " + rvalue + ")";
    }
}

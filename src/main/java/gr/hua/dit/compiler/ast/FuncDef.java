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
        // Only compile the main function for now
        if ("main".equals(name)) {
            // Compile the function body
            body.compile(context);
        }
    }
}

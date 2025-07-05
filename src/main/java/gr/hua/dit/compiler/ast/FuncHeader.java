package gr.hua.dit.compiler.ast;

import java.util.List;
import gr.hua.dit.compiler.types.Type;

public class FuncHeader {
    private String name;
    private List<Decl> params;
    private Type returnType;
    
    public FuncHeader(String name, List<Decl> params, Type returnType) {
        this.name = name;
        this.params = params;
        this.returnType = returnType;
    }
    
    public String getName() { return name; }
    public List<Decl> getParams() { return params; }
    public Type getReturnType() { return returnType; }
    
    public String toString() {
        return "FuncHeader(" + name + ", " + params + ", " + returnType + ")";
    }
}

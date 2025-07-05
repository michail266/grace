package gr.hua.dit.compiler.Symbol;


import java.util.Deque;
import java.util.LinkedList;

import gr.hua.dit.compiler.types.Type;
import gr.hua.dit.compiler.types.BasicType;
import gr.hua.dit.compiler.types.FuncType;
import gr.hua.dit.compiler.types.ArrayType;

public class SymbolTable {

    public SymbolTable() {
        scopes = new LinkedList<>();

        // enter the initial scope
        openScope();
        
        // Add built-in functions
        addBuiltinFunctions();
    }
    
    private void addBuiltinFunctions() {
        // strlen function: takes char[] and returns int
        addEntry("strlen", new FuncType(java.util.Arrays.asList((Type)new ArrayType(BasicType.Char)), BasicType.Int));
        
        // puts function: takes char[] and returns nothing
        addEntry("puts", new FuncType(java.util.Arrays.asList((Type)new ArrayType(BasicType.Char)), null));
        
        // geti function: takes nothing and returns int
        addEntry("geti", new FuncType(java.util.Collections.emptyList(), BasicType.Int));
        
        // puti function: takes int and returns nothing
        addEntry("puti", new FuncType(java.util.Arrays.asList((Type)BasicType.Int), null));
    }

    public SymbolEntry lookup(String sym) {
        Scope s = scopes.getFirst();
        return s.lookupEntry(sym);
    }

    // recurse through all scopes
    public SymbolEntry lookupRec(String sym) {
        // first scope in the list in the most recent
        for (Scope s : scopes) {
            SymbolEntry e = s.lookupEntry(sym);
            if (e != null)
                return e;
        }
        return null;
    }

    public void addEntry(String sym, Type t) {
      Scope s = scopes.getFirst();
      s.addEntry(sym, new SymbolEntry(sym, t));
    }

    public void openScope() {
        scopes.addFirst(new Scope());
    }

    public void closeScope() {
        scopes.removeFirst();
    }

    private Deque<Scope> scopes;
}

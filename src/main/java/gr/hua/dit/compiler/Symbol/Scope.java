package gr.hua.dit.compiler.Symbol;

import java.util.Map;
import java.util.HashMap;

public class Scope {
    
    public Scope() {
        locals = new HashMap<String,SymbolEntry>();
    }

    public SymbolEntry lookupEntry(String sym) {
        return locals.get(sym);
    }

    public void addEntry(String sym, SymbolEntry entry) {
        locals.put(sym, entry);
    }

    private Map<String, SymbolEntry> locals;
}

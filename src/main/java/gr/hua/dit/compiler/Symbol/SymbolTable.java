package gr.hua.dit.compiler.Symbol;


import java.util.Deque;
import java.util.LinkedList;

import gr.hua.dit.compiler.types.Type;

public class SymbolTable {

    public SymbolTable() {
        scopes = new LinkedList<>();

        // enter the initial scope
        openScope();
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

package gr.hua.dit.compiler.Symbol;

import gr.hua.dit.compiler.types.Type;

public class SymbolEntry {
    private String s;
    private Type t;

    public SymbolEntry(String s, Type t) {
      this.s = s;
      this.t = t;
    }

    public Type getType() { return t; }
}

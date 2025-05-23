package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.types.*;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.Symbol.*;

public class Decl extends ASTNode {

    private String id;
    private Type type;

    public Decl(String id, Type t) {
        this.id = id;
        this.type = t;
    }

    public String toString() { return "Decl(" + id + "," + type + ")"; }

    public void sem(SymbolTable tbl) {
      tbl.addEntry(id, type);
    }
}

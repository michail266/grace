package gr.hua.dit.compiler.ast;

import gr.hua.dit.compiler.Symbol.SymbolTable;
import gr.hua.dit.compiler.errors.SemanticException;
import gr.hua.dit.compiler.types.ArrayType;
import gr.hua.dit.compiler.types.BasicType;

public class ArrayAccess extends Expr {
    private Expr array;
    private Expr index;
    
    public ArrayAccess(Expr array, Expr index) {
        this.array = array;
        this.index = index;
    }
    
    public Expr getArray() {
        return array;
    }
    
    public Expr getIndex() {
        return index;
    }
    
    @Override
    public void sem(SymbolTable symbolTable) throws SemanticException {
        // Semantic analysis for array and index
        array.sem(symbolTable);
        index.sem(symbolTable);
        
        // Check that index is an integer
        if (!index.getType().equals(BasicType.Int)) {
            throw new SemanticException("Array index must be of type int");
        }
        
        // Check that array is actually an array type
        if (!(array.getType() instanceof ArrayType)) {
            throw new SemanticException("Cannot index non-array type");
        }
        
        // The type of array access is the element type of the array
        ArrayType arrayType = (ArrayType) array.getType();
        type = arrayType.getElementType();
    }
    
    @Override
    public String toString() {
        return "ArrayAccess(" + array + "[" + index + "])";
    }
}

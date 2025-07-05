package gr.hua.dit.compiler.types;

public class ArrayType extends Type {
    private Type elementType;
    
    public ArrayType(Type elementType) {
        this.elementType = elementType;
    }
    
    public Type getElementType() {
        return elementType;
    }
    
    @Override
    public String toString() {
        return elementType.toString() + "[]";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArrayType) {
            ArrayType other = (ArrayType) obj;
            return elementType.equals(other.elementType);
        }
        return false;
    }
    
    public boolean equals(Type t) {
        if (t instanceof ArrayType) {
            ArrayType other = (ArrayType) t;
            return elementType.equals(other.elementType);
        }
        return false;
    }
}

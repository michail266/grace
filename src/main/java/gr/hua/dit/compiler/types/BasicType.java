package gr.hua.dit.compiler.types;

public class BasicType extends Type {
    
    private enum BasicTypeEnum {
        CHAR,
        INT
    };

    static public BasicType Int = new BasicType(BasicTypeEnum.INT);
    static public BasicType Char = new BasicType(BasicTypeEnum.CHAR);

    private BasicTypeEnum type;

    private BasicType(BasicTypeEnum e) {
        this.type = e;
    }

    public String toString() { return type.toString(); }

    public boolean equals(Type t) {
        if (t instanceof BasicType) {
            BasicType that = (BasicType)t;
            return this.type == that.type;
        }
        return false;
    }
}

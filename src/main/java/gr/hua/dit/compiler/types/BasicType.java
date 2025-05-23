package gr.hua.dit.compiler.types;

public class BasicType extends Type {
    
    private enum BasicTypeEnum {
        BOOL,
        INT
    };

    static public BasicType Int = new BasicType(BasicTypeEnum.INT);
    static public BasicType Bool = new BasicType(BasicTypeEnum.BOOL);

    private BasicTypeEnum type;

    private BasicType(BasicTypeEnum e) {
        type = e;
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

package cs536.staticSemantics.types;

public class NullType extends Type
{
    // Singleton pattern (see project description)
    private static NullType instance = new NullType();
    
    public static NullType make() {
        return instance;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    // Instance method

    /** Call NullType.make() instead */
    private NullType() {
    }

    public int getSize() {
        return -1;
    }

    public String toString() {
	return "<null type>";
    }
}

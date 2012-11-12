package cs536.staticSemantics.types;

public class BoolType extends Type
{
    // Singleton pattern (see project description)
    private static BoolType instance = new BoolType();
    
    public static BoolType make() {
        return instance;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    // Instance method

    /** Call BoolType.make() instead */
    private BoolType() {
    }

    public int getSize() {
        return 4;
    }

    public String toString() {
	return "bool";
    }
}

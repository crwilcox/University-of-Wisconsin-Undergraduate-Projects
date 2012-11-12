package cs536.staticSemantics.types;

public class IntType extends Type
{
    // Singleton pattern (see project description)
    private static IntType instance = new IntType();
    
    public static IntType make() {
        return instance;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    // Instance method

    /** Call IntType.make() instead */
    private IntType() {
    }

    public int getSize() {
		return 4;
    }
}

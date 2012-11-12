package cs536.staticSemantics.types;

public class DoubleType extends Type
{
    // Singleton pattern (see project description)
    private static DoubleType instance = new DoubleType();
    
    public static DoubleType make() {
        return instance;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    // Instance method

    /** Call DoubleType.make() instead */
    private DoubleType() {
    }

    public int getSize() {
		return 8;
    }
}

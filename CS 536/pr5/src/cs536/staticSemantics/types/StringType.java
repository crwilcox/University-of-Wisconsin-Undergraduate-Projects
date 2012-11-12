package cs536.staticSemantics.types;

public class StringType extends Type
{
    // Singleton pattern (see project description)
    private static StringType instance = new StringType();
    
    public static StringType make() {
        return instance;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    // Instance method

    /** Call StringType.make() instead */
    private StringType() {
    }

    public int getSize() {
        return 4;
    }

	public String toString(){
		return "string";
	}
}

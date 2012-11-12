package cs536.staticSemantics.types;

public class ErrorType extends Type
{
    // Singleton pattern (see project description)
    private static ErrorType instance = new ErrorType();
    
    public static ErrorType make() {
        return instance;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    // Instance method

    /** Call ErrorType.make() instead */
    private ErrorType() {
    }

    public int getSize() {
        return -1;
    }

	public String toString(){
		return "Error Type";
	}
}

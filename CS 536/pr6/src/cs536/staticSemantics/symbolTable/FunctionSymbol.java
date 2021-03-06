package cs536.staticSemantics.symbolTable;

import cs536.staticSemantics.types.FunctionType;
import cs536.ast.SourceLocation;

public class FunctionSymbol extends Symbol
{
	
    public FunctionSymbol(String name, FunctionType type, SourceLocation loc) {
        super(name, type, loc);
    }

	private int size;
	public int getSize(){return size;}
	public void setSize(int s){ size = s;}
	
    /**
     * Returns the type of the symbol
     */
    // This is an override, not an overload. That is, it's the "same"
    // getType() function as is in the parent class, despite the fact that it
    // has a different return type. We're taking advantage of "covariant
    // return types" here to specify the particular subclass.
    public FunctionType getType() {
	return (FunctionType) super.getType();
    }


    public String toString() {
	return "[function:" + getName() + "]";
    }
}

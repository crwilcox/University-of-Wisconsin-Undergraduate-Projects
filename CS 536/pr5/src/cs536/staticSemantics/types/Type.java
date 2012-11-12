package cs536.staticSemantics.types;

public abstract class Type
{
    public abstract int getSize();
	public abstract String toString();
	public boolean isPointer() { return false; }
	public boolean isArray() { return false; }
}

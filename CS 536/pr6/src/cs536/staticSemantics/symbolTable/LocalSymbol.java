package cs536.staticSemantics.symbolTable;

import cs536.staticSemantics.types.Type;
import cs536.ast.SourceLocation;

public class LocalSymbol extends VariableSymbol
{
    public LocalSymbol(String name, Type type, SourceLocation loc) {
        super(name, type, loc);
    }

    public String getAnnotationString() {
        return getDeclarationLocation() + ":L";
    }

    public String toString() {
	return "[local:" + getName() + "]";
    }


    private static int InvalidOffset = -100000;
    private int offset = InvalidOffset;

    public int getOffset() {
        assert this.offset != InvalidOffset;
        return offset;
    }

    public void setOffset(int offset) {
        assert this.offset == InvalidOffset;
        this.offset = offset;
    }
}

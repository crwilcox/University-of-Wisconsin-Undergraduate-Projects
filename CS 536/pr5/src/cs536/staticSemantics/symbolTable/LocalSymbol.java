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
}

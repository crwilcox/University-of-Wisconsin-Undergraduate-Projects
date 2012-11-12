package cs536.staticSemantics.symbolTable;

import cs536.staticSemantics.types.Type;
import cs536.ast.SourceLocation;

public class GlobalSymbol extends VariableSymbol
{
    public GlobalSymbol(String name, Type type, SourceLocation loc) {
        super(name, type, loc);
    }


    public String getAnnotationString() {
        return getDeclarationLocation() + ":G";
    }

    public String toString() {
	return "[global:" + getName() + "]";
    }
}


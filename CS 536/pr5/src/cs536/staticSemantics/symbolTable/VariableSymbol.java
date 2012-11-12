package cs536.staticSemantics.symbolTable;

import cs536.staticSemantics.types.Type;
import cs536.ast.SourceLocation;

public abstract class VariableSymbol extends Symbol
{
    public VariableSymbol(String name, Type type, SourceLocation loc) {
        super(name, type, loc);
    }

    public abstract String getAnnotationString();
}

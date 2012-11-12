package cs536.staticSemantics.symbolTable;

import cs536.staticSemantics.types.Type;
import cs536.ast.SourceLocation;

public class VariableSymbol extends Symbol
{
	private Boolean isGlobal;   

	public VariableSymbol(String name, Type type, SourceLocation loc, Boolean i_global) {
        super(name, type, loc);

		this.isGlobal = i_global;
    }

	public Boolean isGlobal()
	{
		return isGlobal;
	}
}

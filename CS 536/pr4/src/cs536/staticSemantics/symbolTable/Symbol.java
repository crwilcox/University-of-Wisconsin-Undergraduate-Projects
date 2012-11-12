package cs536.staticSemantics.symbolTable;

import cs536.staticSemantics.types.Type;
import cs536.ast.SourceLocation;

/**
 * This class represents a generic symbol for a programming language.
 *
 * At least for now, it only consists of the object's identity and the
 * symbol's name.
 */
public abstract class Symbol
{
    /**
     * Creates a Symbol with the given name and type. The location specifies
     * the declaration location. (It should point to the beginning of the
     * IDENT token.)
     *
     * All parameters should be non-null.
     */
    public Symbol(String name, Type type, SourceLocation loc) {
	assert(name != null);
        assert(type != null);
        assert(loc != null);
	this.name = name;
        this.type = type;
        this.loc = loc;
    }

    /**
     * Return the location the symbol was declared at.
     */
    public SourceLocation getDeclarationLocation() {
        return loc;
    }

    /**
     * Returns the Symbol's name
     */
    public String getName() {
	return name;
    }

    /**
     * Returns the type of the symbol
     */
    public Type getType() {
	return type;
    }

    /**
     * Returns the symbol's name, adorned with extra debugging text
     */
    public String toString() {
	return "[sym:" + name + "]";
    }

    /**
     * The name of this Symbol
     */
    private final String name;

    /**
     * The type of this Symbol
     */
    private final Type type;

    /**
     * The location the variable was declared
     */
    private final SourceLocation loc;
}

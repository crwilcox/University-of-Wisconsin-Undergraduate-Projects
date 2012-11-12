package cs536.staticSemantics.symbolTable;

import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

import cs536.staticSemantics.types.Type;

/**
 * This class is a symbol table
 */
public class SymbolTable
{
    /**
     * Construct an empty symbol table. The table will have the global
     * scope already in-place.
     */
    public SymbolTable() {
	// Add the global scope
	startScope(true);
    }

    /**
     * Enters a new scope. (Adds a new scope table to the stack.)
     */
	public void startScope()
	{
		startScope(false);
	}


    public void startScope(boolean i_resetOffset) {
	scopeStack.push(new HashMap<String, Symbol>());
	if(i_resetOffset)
		scopeOffsetStack.push(-4);
	else
		scopeOffsetStack.push(scopeOffsetStack.peek());
    }

    /**
     * Exits the current scope. If the current scope is the globals,
     * throw an EmptyException.
     */
    public void endScope()
        //throws EmptyException
    {
	if (scopeStack.size() > 1) {
	    scopeStack.pop();
		scopeOffsetStack.pop();
	}
	else {
            assert false; // Tried to pop the global scope
	    //throw new EmptyException();
	}
    }

    /**
     * Adds the given symbol to the symbol table at the current
     * scope. (Remember, a symbol knows its name.)
     */

	public int declare(Symbol symbol)
	throws RedeclarationException
{
	return declare(symbol, true);
}

    public int declare(Symbol symbol, boolean update_offset)
	throws RedeclarationException
    {
	Map<String, Symbol> currentScope = scopeStack.peek();
	Symbol existingDeclaration = currentScope.get(symbol.getName());
	if (existingDeclaration != null) {
	    // 'name' was already declared in this scope
	    throw new RedeclarationException(symbol, existingDeclaration);
	}

	// Update this variables offset
	int PrevOffset = 666;
	if(update_offset)
	{
	VariableSymbol varSymbol = (VariableSymbol)symbol;
	Type type = varSymbol.getType();
	PrevOffset = scopeOffsetStack.pop();
	scopeOffsetStack.push(PrevOffset - type.getSize());
	//symbol.setOffset(CurrentOffset);
	}

	currentScope.put(symbol.getName(), symbol);
	return PrevOffset;
	}

    /**
     * Returns the symbol declared with the given name in the
     * innermost scope, or null if there is none.
     */
    public Symbol lookup(String name) {
	for (int level=scopeStack.size()-1; level >=0; --level) {
	    Map<String, Symbol> currentScope = scopeStack.get(level);
	    Symbol declaration = currentScope.get(name);
	    if (declaration != null) {
		return declaration;
	    }
	}

	//////// TestTracker code below
	
	//////// TestTracker code above

	return null;
    }


	Stack<Integer> scopeOffsetStack = new Stack<Integer>();
    Stack<Map<String, Symbol>> scopeStack =	new Stack<Map<String, Symbol>>();
}

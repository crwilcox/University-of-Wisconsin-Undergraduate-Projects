package cs536.staticSemantics.symbolTable;

import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

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
	startScope();
    }

    /**
     * Enters a new scope. (Adds a new scope table to the stack.)
     */
    public void startScope() {
	scopeStack.push(new HashMap<String, Symbol>());
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
    public void declare(Symbol symbol)
	throws RedeclarationException
    {
	Map<String, Symbol> currentScope = scopeStack.peek();
	Symbol existingDeclaration = currentScope.get(symbol.getName());
	if (existingDeclaration != null) {
	    // 'name' was already declared in this scope
	    throw new RedeclarationException(symbol, existingDeclaration);
	}
	currentScope.put(symbol.getName(), symbol);
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


    Stack<Map<String, Symbol>> scopeStack =
	new Stack<Map<String, Symbol>>();
}

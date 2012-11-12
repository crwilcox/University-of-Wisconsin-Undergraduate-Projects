///////////////////////////////////////////////////////////////////////////////
// Main Class File:  P1.java
// File:             SymbolTable.java
// Semester:         Fall 2011
//
// Author:           chris@crwilcox.com
// CS Login:         wilcox
// Lecturer's Name:  Driscoll
//////////////////////////// 80 columns wide //////////////////////////////////
package cs536.staticSemantics.symbolTable;

import java.util.ArrayList;
import java.util.HashMap;


public class SymbolTable
{
	//each HashMap Represents a Scope.  The ArrayList will be used as a LIFO Stack
	private ArrayList<HashMap<String, Symbol>> Stack;
	
	/**
	 * Initialize this SymbolTable object to have a single, empty scope.
	 */
	public SymbolTable(){
		Stack = new ArrayList<HashMap<String, Symbol>>();
		Stack.add(new HashMap<String, Symbol>());
	}
	
	/**
	 * Add a new scope to the stack contained in this symbol table.
	 */
	public void startScope(){
		Stack.add(new HashMap<String,Symbol>());
	}
	
	/**
	 * Remove the topmost scope from the stack in this symbol table; except that if that would 
	 * leave the stack of scopes empty, you should throw an EmptyException instead.
	 * @throws EmptyException
	 */
	public void endScope() throws EmptyException{
		if(Stack.size() == 1){
			//if removing would result in an empty stack, throw an exception
			throw new EmptyException();			
		}
		else{
			//remove the topmost element of the stack
			Stack.remove(Stack.size() - 1);
		}
	}
	
	/**
	 *  Add the symbol sym to the current scope (the one at the top of the stack); 
	 *  except that if there is already a variable with the same name, throw a RedeclarationException.
	 * @param sym
	 * @throws RedeclarationException
	 */
	public void declare( Symbol sym) throws RedeclarationException{
		//if there is already a variable with the same name, throw a RedeclarationException.
		if(Stack.get(Stack.size() - 1).containsKey(sym.getName())){
			throw new RedeclarationException();
		}
		//Else, Add the symbol sym to the current scope (the one at the top of the stack); 
		else{
			Stack.get(Stack.size() - 1).put(sym.getName(), sym);
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return If the given name is declared in any scope, return the symbol corresponding to that name in the scope nearest the top; otherwise return null.
	 */
	public Symbol lookup( String name){
		//try to find the name starting nearest the top
		for(int i = Stack.size() - 1; i >= 0; i--){
			if(Stack.get(i).containsKey(name)){
				return Stack.get(i).get(name);
			}
		}
		
		//if we don't find it, return null
		return null;
		
	}
	
	/**
	 * Return a string representation of the contents of the SymbolTable. 
	 * I highly encourage you to also print divisions between the separate scopes in the stack.
	 */
	public String toString(){
		String retString = "";
		
		for(int i = Stack.size() - 1; i >= 0; i--){
			retString += Stack.get(i).values().toString();
			retString += "\n";
		}
		
		return retString;
	}
}
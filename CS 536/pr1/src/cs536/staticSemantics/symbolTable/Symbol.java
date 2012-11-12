///////////////////////////////////////////////////////////////////////////////
// Main Class File:  P1.java
// File:             Symbol.java
// Semester:         Fall 2011
//
// Author:           chris@crwilcox.com
// CS Login:         wilcox
// Lecturer's Name:  Driscoll
//////////////////////////// 80 columns wide //////////////////////////////////
package cs536.staticSemantics.symbolTable;

public class Symbol
{
	private String name;
	private String type;
	/**
	 * The class constructor: initialize this Symbol to have the given name and type. (Precondition: name and type are not null.)
	 * @param name Name of the symbol
	 * @param type Type of the symbol
	 * @throws Exception 
	 */
	public Symbol (String name, String type){
		this.name = name;
		this.type = type;
	}
	
	/**
	 * 
	 * @return Return the name of this Symbol.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @return Return the type of this Symbol.
	 */
	public String getType(){
		return type;
		
	}
	
	/**
	 * @return Return a readable representation of the Symbol.
	 */
	public String toString(){
		return String.format("Name: %-8s Type: %-8s", name, type);
	}
}
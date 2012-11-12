///////////////////////////////////////////////////////////////////////////////
// Main Class File:  P1.java
// File:             EmptyException.java
// Semester:         Fall 2011
//
// Author:           chris@crwilcox.com
// CS Login:         wilcox
// Lecturer's Name:  Driscoll
//////////////////////////// 80 columns wide //////////////////////////////////
package cs536.staticSemantics.symbolTable;

public class EmptyException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	Classes RedeclarationException and EmptyException
	
	These two classes are empty, used to signal attempts to add a name that is already declared 
	in the current scope and attempts to end the outermost scope. Both need to inherit from 
	java.lang.Exception so that you can throw instances of these types.
	
	You may find it useful to store information. For instance, the RedeclarationException 
	could store a reference to the symbol the user attempted to declare and/or the existing 
	symbol with that name. (This could be used for better error reporting. For instance, 
	if you try to redeclare a variable in code you give to GCC, it will tell you both where your 
	redeclaration is as well as the original declaration.)
	*/
}
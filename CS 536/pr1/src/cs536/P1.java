///////////////////////////////////////////////////////////////////////////////
// Title:            pr1 - SymbolTable
// Files:            P1.java, EmptyException.java, RedeclarationException.java,
//                   Symbol.java, SymbolTable.java
// Semester:         Fall 2011
//
// Author:           Christopher Wilcox (chris@crwilcox.com)
// CS Login:         wilcox
// Lecturer's Name:  Driscoll
//
// Description:      Implementation of the first project.  the first project
//                   implements a block-structured symbol table.
///////////////////////////////////////////////////////////////////////////////
/*
Conceptually, the compiler tracks a stack of scopes that are active at the current point. 
For instance, when the compiler is considering the expression labeled c, the global scope 
and foo's scope are active but the scope for the if statement is not. Thus there will be two scopes on the stack.

Each scope will map identifiers (as strings) to the symbol they actually represent. A symbol is the 
programming language entity that an identifier refers to. (It would be reasonable to say that an identifier, 
with scope information, names a symbol.) A symbol can be a variable, a function, a type, or any of a few other kinds 
of entities different programming languages provide. In our language, we will have variables and functions.

The individual tables that represent scopes in the symbol table stack can be implemented as a dictionary that 
maps names of symbols to the actual Symbol instance. You can use implementations of the java.util.Map interface, such as java.util.HashMap.

Even though you could get away for this assignment with having the dictionary map names of symbols to their types (as strings), 
I strongly recommend that you create an explicit Symbol object. In a later project, you will likely want to store additional 
information with a symbol, and if you have an explicit class that means you only need to add that information to the Symbol 
class and your SymbolTable can remain unchanged.
*/

package cs536;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import cs536.staticSemantics.symbolTable.EmptyException;
import cs536.staticSemantics.symbolTable.RedeclarationException;
import cs536.staticSemantics.symbolTable.Symbol;
import cs536.staticSemantics.symbolTable.SymbolTable;

class P1
{
    /**
     *  This reads in lines of input, for each line splits it into tokens,
     *  and outputs each token.
     */
    public static void main(String[] args) {
    	boolean DEBUG = false;

        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);

        SymbolTable symbolTable = new SymbolTable();
        
        String line;
        try {
            while ((line = in.readLine()) != null) {
                //trim line
            	line = line.trim();
            	//process line
            	//Start by making sure the line isn't blank or null somehow
            	if(line != null && line.length() > 0){
                    if(DEBUG){
                    	System.out.println("Line: " + line);
                    }

                    
            		// currently a line should start with =, ?, {, }
	            	String[] token = line.split(" ");
	            	
	            	//START OF SCOPE
	            	if(token[0].equals("{")){
	            		symbolTable.startScope();
	            	}
	            	//END OF SCOPE
	            	else if(token[0].equals("}")){
	            		try{
	            			symbolTable.endScope();
	            		}
	            		catch(EmptyException ee){
	            			System.out.println("invalid");
	            		}
	            	}
	            	//LOOKUP
	            	else if(token[0].equals("?")){
	            		if(token.length != 2){
	            			//this is not a known format
	            			throw new IOException("There was an '?' token that did not have a single entry after it");
	            		}
	            		else{
	            			//find a symbol. If we find it, print type, else print undeclared
	            			Symbol lookup = symbolTable.lookup(token[1]);
	            			if(lookup == null){
	            				//if not declared, report undeclared
	            				System.out.println("undeclared");
	            			}
	            			else{
	            				//if declared, print type
	            				System.out.println(lookup.getType());
	            			}
	            		}
	            	}
	            	//DECLARATION
	            	else if(token[0].equals("=")){
	            		if(token.length != 3){
	            			//this is not a known format
	            			throw new IOException("There was an '=' token that did not have two entries after it");
	            		}
	            		else{
	            			try{
	            				//attempt to declare symbol
	            				symbolTable.declare(new Symbol(token[1], token[2]));
	            			}
	            			catch(RedeclarationException re){
	            				//if already declared, redeclaration
	            				System.out.println("redeclaration");
	            			}
	            		}
	            	}
	            	else{
	            		//unexpected input (token[0] was an unknown entry)
	            		throw new IOException("There was an unexpected starting token on the line.  Starting Token: '" + token[0] + "' Line should start with {, }, =, or ?");
	            	}
            	}
            	if(DEBUG){
            		System.out.println(symbolTable.toString());
            	}
            }
        }
        catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }        
    }
}

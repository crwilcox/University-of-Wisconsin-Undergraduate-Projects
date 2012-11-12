package cs536.syntax;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognizerSharedState;

/**
 * A simple interface to the ANTLR Token class
 * 
 * This handles setting up some of the required metadata based on the recognizer
 * state in one place so that other token types do not need to deal with it
 * directly.
 */
public class MinCIntLitToken extends CommonToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MinCIntLitToken(CharStream stream, RecognizerSharedState state,
			int stopIndex) {
		super(stream, state.type, state.channel, state.tokenStartCharIndex,
				stopIndex);
		setLine(state.tokenStartLine);
		setText(state.text);
		setCharPositionInLine(state.tokenStartCharPositionInLine);
	}
	
	public String toString(){
		switch(type){
		//INTEGER LITERALS
			case MinC.INT_LIT:	
				//INTEGER OUT OF RANGE  DOING THIS IN THE PARENT CLASS
				//use a long to parse so if it is on the bubble it is not cut
				try{
					long longValue = Long.parseLong(getText());
					//System.out.println("Value" + value + " MIN VALUE: "  + Integer.MIN_VALUE );
					if(longValue > -((long)Integer.MIN_VALUE)){
						throw new NumberFormatException();
					}
					return "INT_LIT: " + getText();
				}
				catch(NumberFormatException e){
					return "Integer out of range: " + getText();
				}
				
				
			case MinC.LEADING_INT_LIT: 
									return "Integer has leading 0: " + getText();
					
			default:				return "DEFAULT - SHOULD NOT GET HERE: TYPE NO: " + type + " TEXT:" + getText();
		
		}
	}
	
}

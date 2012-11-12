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
public class MinCFloatLitToken extends CommonToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double value;
	public MinCFloatLitToken(CharStream stream, RecognizerSharedState state,
			int stopIndex) {
		super(stream, state.type, state.channel, state.tokenStartCharIndex,
				stopIndex);
		setLine(state.tokenStartLine);
		setText(state.text);
		setCharPositionInLine(state.tokenStartCharPositionInLine);
	}
	
	public String toString(){
		//Print the float, but check first if it is too big
		//FLOATING-POINT LITERALS
		switch(type){
			case MinC.FLOAT_LIT:	
				try{
					value = Double.parseDouble(getText());
					if(Double.isInfinite(value) | Double.isNaN(value)) throw new NumberFormatException();
					return "FLOAT_LIT: " + Double.toString(value);
				}
				catch(NumberFormatException e){
					return "Floating point out of range: " + getText();
				}
				
			
			case MinC.LEADING_FLOAT_LIT:
									return "Floating point has leading 0: " + getText();
									
			default:				return "DEFAULT - SHOULD NOT GET HERE: TYPE NO: " + type + " TEXT:" + getText();
		}
	}
	
}

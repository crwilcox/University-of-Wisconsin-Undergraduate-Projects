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
public class MinCStringLitToken extends CommonToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MinCStringLitToken(CharStream stream, RecognizerSharedState state,
			int stopIndex) {
		super(stream, state.type, state.channel, state.tokenStartCharIndex,
				stopIndex);
		setLine(state.tokenStartLine);
		setText(state.text);
		setCharPositionInLine(state.tokenStartCharPositionInLine);
	}
	
	public String toString(){
		String text;
		switch(type){
			//STRING LITERALS
			case MinC.STRING_LIT:	return "STRING_LIT: " + getText();
			//TODO: Invalid strings (3 different Error tokens for this)
			
			case MinC.ILLEGAL_STRING_CHARACTER:
									return "Illegal string character";
			case MinC.ILLEGAL_ESCAPE:
									text = getText();
									//get index of the first bad escape
									//ESCAPE_SEQUENCES : '\\n' | '\\t' | '\\r' | '\\\\' | '\\"'; 
									boolean badSequenceFound = false;
									int ind = 0;
									while(!badSequenceFound | text.length() == 0){
										ind = text.indexOf('\\');
										if(text.charAt(ind+1) != 'n'
											&&	text.charAt(ind+1) != 't'
											&&	text.charAt(ind+1) != 'r'
											&&	text.charAt(ind+1) != '\\'
											&&	text.charAt(ind+1) != '"'
											){
											badSequenceFound = true;
										}
										else{
											text = text.substring(ind+1);
										}
									}
									return "Illegal escape sequence: " + text.substring(ind,  ind+2);
			
			case MinC.UNTERMINATED_STRING:
									//remove the new line character from print
									text = getText().replaceAll("\n|\r", "");
									return "Unterminated string: " + text;

									
			default:				return "DEFAULT - SHOULD NOT GET HERE: TYPE NO: " + type + " TEXT:" + getText();
		}
	}
	
}

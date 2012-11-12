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
public class MinCToken extends CommonToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MinCToken(CharStream stream, RecognizerSharedState state,
			int stopIndex) {
		super(stream, state.type, state.channel, state.tokenStartCharIndex,
				stopIndex);
		setLine(state.tokenStartLine);
		setText(state.text);
		setCharPositionInLine(state.tokenStartCharPositionInLine);
	}
	
	public String toString(){
		switch(type){
			//NON PRINTING CHARACTER
			case MinC.SPACE: 		return "SPACE: SHOULD NOT PRINT";
			case MinC.COMMENT:		return "COMMENT: SHOULD NOT PRINT: " + getText();
			case MinC.BLOCK_COMMENT:return "BLOCK_COMMENT: SHOULD NOT PRINT: " + getText();
			case MinC.UNTERMINATED_BLOCK_COMMENT:
									return "Unterminated block comment";
			
			//KEYWORDS
			case MinC.BOOL:			return "BOOL"; 
			case MinC.DOUBLE:		return "DOUBLE";
			case MinC.ELSE:			return "ELSE";
			case MinC.FALSE:		return "FALSE";
			case MinC.IF:			return "IF";
			case MinC.INT:			return "INT";
			case MinC.NULL:			return "NULL";
			case MinC.READ:			return "READ";
			case MinC.RETURN:		return "RETURN";
			case MinC.STRING:		return "STRING";
			case MinC.TRUE:			return "TRUE";
			case MinC.VOID:			return "VOID";
			case MinC.WHILE:		return "WHILE";
			case MinC.WRITE:		return "WRITE";
			
			//OPERATORS
			case MinC.LPAREN:		return "LPAREN";
			case MinC.RPAREN:		return "RPAREN";
			case MinC.LBRACE:		return "LBRACE";
			case MinC.RBRACE:		return "RBRACE";
			case MinC.LBRACKET:		return "LBRACKET";
			case MinC.RBRACKET:		return "RBRACKET";
			case MinC.SEMI:			return "SEMI";
			case MinC.COMMA:		return "COMMA";
			case MinC.LAND:			return "LAND";
			case MinC.LOR:			return "LOR";
			case MinC.LNOT:			return "LNOT";
			case MinC.ASSIGN:		return "ASSIGN";
			case MinC.ADDROF:		return "ADDROF";
			case MinC.LT:			return "LT";
			case MinC.LTE:			return "LTE";
			case MinC.GT:			return "GT";
			case MinC.GTE:			return "GTE";
			case MinC.EQUAL:		return "EQUAL";
			case MinC.NEQUAL:		return "NEQUAL";
			case MinC.INCR:			return "INCR";
			case MinC.DECR:			return "DECR";
			case MinC.PLUS:			return "PLUS";
			case MinC.MINUS:		return "MINUS";
			case MinC.STAR:			return "STAR";
			case MinC.DIV:			return "DIV";
			case MinC.MOD:			return "MOD";
			
			//IDENTIFIERS
			case MinC.IDENT:		return "IDENT: " + getText();
			//TODO: Invalid Identifier
			case MinC.INVALID_IDENT:
									return "Invalid identifier: " + getText();
			
			default:				return "DEFAULT - SHOULD NOT GET HERE: TYPE NO: " + type + " TEXT:" + getText();
		}
	}
	
}

package cs536.syntax;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognizerSharedState;

/**
   A simple interface to the ANTLR Token class

   This handles setting up some of the required metadata based on the
   recognizer state in one place so that other token types do not
   need to deal with it directly.
 */
public class MinCToken extends CommonToken
{
    private final String representation;
    
    public MinCToken(String representation, int type, CharStream stream, RecognizerSharedState state, int stopIndex)
    {
        super(stream, state.type, state.channel, state.tokenStartCharIndex, stopIndex);
        setLine(state.tokenStartLine);
        setText(state.text);
        setType(type);
        setCharPositionInLine(state.tokenStartCharPositionInLine);

        this.representation = representation;
    }


    public String getRepresentation() {
        return representation;
    }

    public String toString() {
        return getRepresentation();
    }
}

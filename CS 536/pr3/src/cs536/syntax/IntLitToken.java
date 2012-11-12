package cs536.syntax;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognizerSharedState;

public class IntLitToken extends MinCToken
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private final long value;

  public IntLitToken(long value, int type, CharStream stream, RecognizerSharedState state, int stopIndex)
  {
    super("INT_LIT: " + value, type, stream, state, stopIndex);
    this.value = value;
  }

  public long getValue()
  {
    return value;
  }
}

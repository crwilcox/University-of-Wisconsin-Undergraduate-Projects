package cs536.syntax;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognizerSharedState;

public class FloatLitToken extends MinCToken
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private final double value;

  public FloatLitToken(double value, int type, CharStream stream, RecognizerSharedState state, int stopIndex)
  {
    super("FLOAT_LIT: " + value, type, stream, state, stopIndex);
    this.value = value;
  }

  public double getValue()
  {
    return value;
  }
}

package cs536;

//import java.io.InputStream;
//import java.io.FileInputStream;
import java.io.IOException;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Token;
import cs536.syntax.MinCLexer;

public class P2
{
  public static void main(String argv[])
  {
    try
    {
      // Create the lexer using an ANTLR-wrapped stream
      ANTLRInputStream input = new ANTLRInputStream(System.in);
      MinCLexer lexer = new MinCLexer(input);

      // Print out each token.
      Token t;
      do
      {
        t = lexer.nextToken();
        if(t.getType() == Token.EOF)
          break;

        System.out.println(t.getLine() + ":" + (t.getCharPositionInLine()+1) + ":" + t);
      } while(true);
    }
    catch(IOException ex)
    {
      System.exit(1);
    }
  }
}
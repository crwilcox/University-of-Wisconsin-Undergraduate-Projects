package cs536;

import java.io.FileInputStream;
import java.io.IOException;
//import java.util.List;
//import java.util.Iterator;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import cs536.ast.AbstractSyntaxTree;
import cs536.ast.Unparser;
import cs536.syntax.MinCParser;
import cs536.syntax.MinCLexer;

public class P3
{
  public static void main(String argv[])
  {
    try
    {
      ANTLRInputStream input;

      if(argv.length == 0 || argv[0].equals("-"))
      {
        input = new ANTLRInputStream(System.in);
      }
      else
      {
        input = new ANTLRInputStream(new FileInputStream(argv[0]));
      }

      MinCLexer lexer = new MinCLexer(input);
      MinCParser parser = new MinCParser(new CommonTokenStream(lexer));

      AbstractSyntaxTree ast = new AbstractSyntaxTree(parser.program());

      if (lexer.isSuccessful() && parser.isSuccessful()) {
          String out = ast.accept(new Unparser());
          System.out.println(out);
      }
    }
    catch(RecognitionException ex)
    {
      System.err.println(ex);
    }
    catch(IOException ex)
    {
      System.exit(1);
    }
  }
}

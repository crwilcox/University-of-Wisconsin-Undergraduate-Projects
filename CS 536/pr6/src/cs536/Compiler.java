package cs536;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import cs536.ast.AbstractSyntaxTree;
import cs536.ast.Unparser;
import cs536.ast.TypesUnparser;
import cs536.syntax.MinCParser;
import cs536.syntax.MinCLexer;
import cs536.staticSemantics.types.TypeChecker;
import cs536.staticSemantics.types.LvalueChecker;
import cs536.codegen.mips.CodeGenerator;

public class Compiler
{
  public static void main(String argv[])
  {
    try
    {
      ANTLRInputStream input;

      // New to project 3! For your convenience, if you specify a file name
      // on the command line, we will open and parse that file instead of
      // standard input. You don't have to do this, but lots of people were
      // modifing the code to do so anyway.
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

      // If there was a syntax error we discovered during parsing, then we
      // don't continue.
      if (parser.isSuccessful()) {
          boolean namesSuccessful = ast.resolveNames();

          if (namesSuccessful) {
              TypeChecker typeChecker = new TypeChecker();
              LvalueChecker lvalueChecker = new LvalueChecker();
              
              ast.accept(typeChecker);
              ast.accept(lvalueChecker);

              if (typeChecker.passed() && lvalueChecker.passed()) {
		  System.out.println(ast.accept(new CodeGenerator()));
              }
          }
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

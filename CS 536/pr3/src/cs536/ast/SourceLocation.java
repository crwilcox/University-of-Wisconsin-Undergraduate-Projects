package cs536.ast;

import org.antlr.runtime.Token;
import static cs536.ast.AbstractSyntaxTree.AstNode;

public class SourceLocation
{
  private int rowNum;
  private int colNum;

  public SourceLocation(Token tok)
  {
    rowNum = tok.getLine();
    colNum = tok.getCharPositionInLine() + 1;
  }

  public SourceLocation(AstNode node)
  {
    rowNum = node.getLocation().getRow();
    colNum = node.getLocation().getCol();
  }

  public int getRow()
  {
    return rowNum;
  }

  public int getCol()
  {
    return colNum;
  }
}